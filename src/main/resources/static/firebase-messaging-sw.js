/*
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
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body
  }
  console.log(notificationTitle);
  console.log(notificationOptions);
  
  
  // Service Worker가 직접 알림을 화면에 띄우는 코드 (OS 팝업)
  return self.registration.showNotification(notificationTitle, notificationOptions);
});
*/
// v8 SDK를 사용하므로, importScripts로 라이브러리를 가져옴
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

// JSP에 썼던 firebaseConfig 객체를 똑같이 넣어줌
var firebaseConfig = {
  apiKey: "AIzaSyCFgi-2XKeO727LNumW6uAcCtaSRUu31fw", // 실제 키로 변경하세요
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

// Service Worker에 푸시 이벤트 리스너를 추가하여 FCM 메시지를 처리합니다.
// onBackgroundMessage 대신 표준 push 이벤트를 사용하고, 
// event.waitUntil을 사용하여 비동기 알림 표시 작업이 완료될 때까지 Service Worker가 종료되지 않도록 보장합니다.

self.addEventListener('push', function(event) {
  // FCM 메시지 페이로드를 가져옵니다.
  // FCM 메시지는 data 필드에 JSON으로 들어오거나 notification 필드로 들어옵니다.
  const payload = event.data.json();
  console.log('[firebase-messaging-sw.js] 백그라운드 메시지 수신:', payload);

  // notification 필드가 없는 경우 (Data Only Message), 알림을 수동으로 구성해야 합니다.
  const notificationPayload = payload.notification || {};
  
  const notificationTitle = notificationPayload.title || '새로운 알림';
  const notificationBody = notificationPayload.body || (payload.data && payload.data.body) || '내용 없음';
  
  const notificationOptions = {
    body: notificationBody,
    // data 필드를 알림에 포함시켜 클릭 시 사용 가능하게 합니다.
    data: payload.data,
    // (선택 사항) 아이콘, 사운드 등을 추가할 수 있습니다.
    // icon: '/images/icon-192x192.png',
  };

  // self.registration.showNotification 호출이 완료될 때까지 Service Worker가 유지되도록 합니다.
  const notificationPromise = self.registration.showNotification(notificationTitle, notificationOptions);

  // 비동기 작업이 완료될 때까지 Service Worker를 유지합니다.
  event.waitUntil(notificationPromise);
});

// onBackgroundMessage는 이제 push 리스너가 처리하므로 삭제하거나 주석 처리합니다.
// messaging.onBackgroundMessage(function(payload) {
//   // ... 기존 로직 삭제 또는 주석 처리
// });