package com.bscalendar.fcm.service;

// (★ import 추가/변경)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.redis.service.RedisService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;


@Service
public class FcmPushService {

    @Autowired
    private RedisService redisService; 

    @Autowired
    private FirebaseMessaging firebaseMessaging; 

    /**
     * 특정 사용자에게 푸시 알림을 발송합니다.
     */
    public void sendNotificationToUser(String userId, String title, String body) {
        
        String redisKey = "fcm:user:" + userId;
        String token = redisService.getData(redisKey);

        if (token == null || token.isEmpty()) {
            System.out.println("알림 발송 실패: UserID " + userId + "의 FCM 토큰이 Redis에 없습니다.");
            return;
        }
        
        // 2. "웹 브라우저" 전용 알림(WebpushNotification) 객체 생성
        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setNotification(WebpushNotification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        // 3. Message 객체를 만들 때 setNotification 대신 setWebpushConfig를 사용합니다.
        Message message = Message.builder()
                .setToken(token)
                .setWebpushConfig(webpushConfig)
                .build();

        // 4. 메시지 발송
        try {
            String response = firebaseMessaging.send(message);
            System.out.println("알림 발송 성공 (User: " + userId + "), Message ID: " + response);
        } catch (FirebaseMessagingException e) { // (★ Exception 타입 변경)
            System.out.println("알림 발송 실패 (User: " + userId + "): " + e.getMessage());
            // (참고) 토큰 만료 시 처리 로직
            if ("UNREGISTERED".equals(e.getMessagingErrorCode().name())) {
                System.out.println(userId + "의 토큰이 만료되어 Redis에서 삭제합니다.");
            }
        }
    }
}