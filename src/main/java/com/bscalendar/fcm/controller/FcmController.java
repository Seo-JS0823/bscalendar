package com.bscalendar.fcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bscalendar.redis.service.RedisService;

import java.util.Map;

@RestController
@RequestMapping("/api/fcm") // fetch로 호출한 주소
public class FcmController {

    @Autowired
    private RedisService redisService; 

    /*
     클라이언트(JSP)로부터 FCM 토큰을 받아 Redis에 저장합니다.
     (URL: POST /api/fcm/token)
     */
    @PostMapping("/token")
    public ResponseEntity<String> saveFcmToken(
            @RequestBody Map<String, String> payload, // { "token": "..." } 형식의 JSON을 받음
            Authentication authentication) {          // JwtAuthenticationFilter가 넣어준 인증 정보
        
        // 1. JSP(fetch)가 body에 담아 보낸 FCM 토큰
        String fcmToken = payload.get("token");

        // 2. JWT 인증 정보에서 "진짜" 로그인한 사용자 ID(username) 가져오기
        String loginMemberId = authentication.getName(); 

        if (fcmToken == null || fcmToken.isEmpty()) {
            return ResponseEntity.badRequest().body("FCM 토큰이 비어있습니다.");
        }
        
        if (loginMemberId == null) {
            return ResponseEntity.status(401).body("인증되지 않은 사용자입니다.");
        }

        try {
            // Redis에 (사용자ID, 토큰) 저장
            // 예시 : "fcm:user:tengen" 라는 Key에 FCM 토큰 값을 저장
            String redisKey = "fcm:user:" + loginMemberId; 
            redisService.saveData(redisKey, fcmToken);
            
            System.out.println("FCM 토큰 저장 완료 (User: " + loginMemberId + ", Token: " + fcmToken.substring(0, 10) + "...)");
            return ResponseEntity.ok("FCM 토큰이 성공적으로 저장되었습니다.");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("토큰 저장 중 서버 오류 발생");
        }
    }
}