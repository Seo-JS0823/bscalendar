package com.bscalendar.fcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.redis.service.RedisService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FcmPushService {

    @Autowired
    private RedisService redisService; //Redis에서 토큰을 가져올 서비스

    @Autowired
    private FirebaseMessaging firebaseMessaging; // FCMConfig에서 만든 알림 발송 객체

     // 특정 사용자에게 푸시 알림을 발송합니다.
     // @param userId 알림을 받을 사용자의 ID
     // @param title 알림 제목
     //  @param body 알림 본문
    public void sendNotificationToUser(String userId, String title, String body) {
        
        // 1. Redis에서 사용자의 FCM 토큰 조회
        String redisKey = "fcm:user:" + userId;
        String token = redisService.getData(redisKey); // RedisTestService의 메서드 사용

        if (token == null || token.isEmpty()) {
            System.out.println("알림 발송 실패: UserID " + userId + "의 FCM 토큰이 Redis에 없습니다.");
            return;
        }

        // 2. 알림 메시지 객체 생성
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        // 3. FCM 페이로드 구성 (기기, 내용)
        Message message = Message.builder()
                .setToken(token) // 이 토큰을 가진 기기로 발송
                .setNotification(notification)
                // (선택) 알림 클릭 시 이동할 URL 등 데이터 추가
                // .putData("click_action", "/project/list") 
                .build();

        // 4. 메시지 발송
        try {
            String response = firebaseMessaging.send(message);
            System.out.println("알림 발송 성공 (User: " + userId + "), Message ID: " + response);
        } catch (Exception e) {
            System.out.println("알림 발송 실패 (User: " + userId + "): " + e.getMessage());
            // 여기서 e.getMessage()에 "UNREGISTERED" 등이 뜨면,
            // 토큰이 만료된 것이므로 Redis에서 삭제하는 로직을 추가가능
        }
    }
}