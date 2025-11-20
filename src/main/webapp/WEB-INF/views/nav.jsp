<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="top-menu-area">
	<div class="menuItem">
		<button onclick="homeLocation()">Project Home</button>
	</div>
	<div class="menuItem">
		<button onclick="myPageLocation()">마이페이지</button>
	</div>
</nav>
<!-- 알림 기능을 위한 스크립트 -->
<script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"></script>
<script>
    // 1. firebaseConfig 객체
    var firebaseConfig = {
      apiKey: "AIzaSyCFgi-2XKeO727LNumW6uAcCtaSRUu31fw",
      authDomain: "bscalendar-cf0df.firebaseapp.com",
      projectId: "bscalendar-cf0df",
      storageBucket: "bscalendar-cf0df.firebasestorage.app",
      messagingSenderId: "336005990928",
      appId: "1:336005990928:web:59484b0f55858881ee5895"
    };

    // 2. 초기화
    firebase.initializeApp(firebaseConfig);

    // 3. 메시징 객체 가져오기
    const messaging = firebase.messaging();

    // 4. vapidKey 입력
    // 프로젝트 설정 -> 클라우드 메시지 -> 웹 푸시 인증서 ->키 쌍
    const vapidKey = "BOxqvK5Zr8v9qmxkhUKX0O-M0LQJ46e6v-PbEgP3zKb6TW2PL-MqxVs_J0VPUDkfb9qzBDDCtVhH81ViQrNCA_A";

    // 5. 알림 권한 요청 및 토큰 발급 함수
    function requestNotificationPermission() {
      console.log("알림 권한을 요청합니다...");
      Notification.requestPermission().then((permission) => {
        if (permission === 'granted') {
          console.log('알림 권한이 허용되었습니다.');
          
          messaging.getToken({ vapidKey: vapidKey }).then((currentToken) => {
            if (currentToken) {
              console.log('FCM 토큰:', currentToken);
              sendTokenToServer(currentToken);
            } else {
              console.log('FCM 토큰을 발급받지 못했습니다.');
            }
          }).catch((err) => {
            console.log('토큰 발급 중 오류 발생:', err);
          });
        } else {
          console.log('알림 권한이 거부되었습니다.');
        }
      });
    }

    // 6. 서버 전송 함수
    function sendTokenToServer(token) {
      console.log("서버로 토큰 전송 시도:", token);
      fetch('/api/fcm/token', { 
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token') 
          },
          body: JSON.stringify({ token: token })
      })
      .then(response => response.text())
      .then(data => console.log('서버 토큰 저장 응답:', data))
      .catch(err => console.error('서버 전송 오류:', err));
    }

    // 7. 함수 호출
    requestNotificationPermission();
</script>
<script>
	function homeLocation() {
		const url = '/project/list';
		window.location.href = url;
	}
	function myPageLocation() {
		const url = '/member/mypage';
		window.location.href = url;
	}

	function getTokenFromInfo(info) {
		if(!info) {
			console.err('getTokenFromInfo Function Parameter Not Found');
			return;
		}
		
		const token = localStorage.getItem('token');
		if(!token) {
			console.err('Token Not Found in localStorage');
			return;
		}
		const base64Url = token.split('.')[1];
		const infomation = decodeJwt(base64Url); 
		
		return infomation[info];
	}
	
	function decodeJwt(base64Url) {
		if(!base64Url) {
			console.error('Token Payload Not Found');
			return;
		}
		
		const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
		
		const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
	  	return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
	  }).join(''));
		
		return JSON.parse(jsonPayload);
	}
</script>
<script>
messaging.onMessage((payload) => {
	console.log(payload);
})
</script>