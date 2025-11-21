// v8 SDK를 사용하므로, importScripts로 라이브러리를 가져옴
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

//JSP에 썼던 firebaseConfig 객체를 똑같이 넣어줌
var firebaseConfig = {
  apiKey: "AIzaSyCFgi-2XKeO727LNumW6uAcCtaSRUu31fw",
  authDomain: "bscalendar-cf0df.firebaseapp.com",
  projectId: "bscalendar-cf0df",
  storageBucket: "bscalendar-cf0df.firebasestorage.app",
  messagingSenderId: "336005990928",
  appId: "1:336005990928:web:59484b0f55858881ee5895"
};

// 백그라운드에서 Firebase 앱 초기화
firebase.initializeApp(firebaseConfig);

// 백그라운드 메시징 객체
const messaging = firebase.messaging();

// 백그라운드에서 메시지를 받았을 때 실행
messaging.onBackgroundMessage(function(payload) {
  console.log('[firebase-messaging-sw.js] 백그라운드 메시지 수신:', payload);

  // payload에서 알림 제목과 내용을 추출
  const notificationTitle = "FCM 시스템 최종 성공";
  const notificationOptions = {
    body: payload.notification.body, 
    icon: '/img/favicon.png' // (참고: 아이콘 경로는 유지)
  }

  // Service Worker가 직접 알림을 화면에 띄우는 코드 (OS 팝업)
  self.registration.showNotification(notificationTitle, notificationOptions);
});