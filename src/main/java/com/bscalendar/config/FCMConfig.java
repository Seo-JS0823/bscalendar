package com.bscalendar.config;

import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FCMConfig {

    // 키 코드
    private String firebaseKeyFileName = "bscalendar-cf0df-firebase-adminsdk-fbsvc-fa1adef11f.json";

    @PostConstruct
    public void initializeFCM() {
        try {
            // 2. resources 폴더에서 json 파일을 읽기
            ClassPathResource resource = new ClassPathResource(firebaseKeyFileName);
            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            // 3. FirebaseApp 초기화 확인
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("FirebaseApp 초기화 성공!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FirebaseApp 초기화 실패: " + e.getMessage());
        }
    }
}