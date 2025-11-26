package com.bscalendar.fcm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bscalendar.redis.service.RedisService;
import com.bscalendar.reply.dto.AlramDTO;
import com.bscalendar.reply.service.AlramService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FcmPushService {

    @Autowired
    private RedisService redisService; 

    @Autowired
    private AlramService alramService;
    
    @Autowired
    private FirebaseMessaging firebaseMessaging; 

    public void sendNotificationToUser(String userId, String userName, String title, String body, String location) {
        
        // FCM 발송이랑 상관없이 무조건 기록부터 남기기
        try {
            AlramDTO alram = new AlramDTO();
            alram.setAlram_date(new Date());
            alram.setMem_id(userId);
            alram.setMessage(body);
            alram.setTitle(title);
            alram.setRead_flag("N");
            alram.setLocation(location);
            
            boolean alramInsert = alramService.alramInsert(alram);
            if(!alramInsert) System.out.println("DB 알람 등록 실패");
            
        } catch (Exception dbE) {
            System.out.println("DB 알림 저장 실패(무시): " + dbE.getMessage());
        }
        
        // 1. Redis에서 토큰 조회
        String redisKey = "fcm:user:" + userId;
        String token = redisService.getData(redisKey); 

        // 2. 토큰 없으면 여기서 끝 (DB 저장은 이미 위에서 했으니 안심하고 종료)
        if (token == null || token.isEmpty()) {
            System.out.println("FCM 토큰 없음 - DB 저장은 완료, 팝업은 건너뜀 (User: " + userId + ")");
            return;
        }

        // 3. 메시지 만들기
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token) 
                .setNotification(notification)
                .build();

        // 4. 진짜로 발송하기
        try {
            String response = firebaseMessaging.send(message);
            System.out.println("팝업 발송 성공 (User: " + userId + "), ID: " + response);
            
        } catch (Exception e) {
            System.out.println("팝업 발송 실패: " + e.getMessage());
        }
    }
}