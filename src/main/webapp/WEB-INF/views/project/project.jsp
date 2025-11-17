<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="/css/weather.css">
  <link rel="stylesheet" href="/css/reset.css">
  <link rel="stylesheet" href="/css/layout.css">
  <title>부성카렌다</title>
  
	<script src="/js/index.global.min.js"></script>
	<script src="/js/calendar.js"></script>
</head>
<body>

<nav class="top-menu-area">
	<!-- JSP include -->
	<!--
	<script>
		JWT MEMBER_ID Parsing
		전역 변수로 MEMBER_ID 저장
		모든 .jsp 파일에서 사용 가능
	</script>
	-->
</nav>

<div class="container">
  <div class="content-area">

    <!-- 달력 , 날씨 -->
    <div class="content col">
      <!-- 달력 Layer -->
      <div class="calendar-container">
        <div class="calendar-descript">
          <div>
            <span class="cal-descript green">● 팀 업무</span>
            <span class="cal-descript orange">● 개인 업무</span>
            <span class="cal-descript blue" id="member_name"></span>
            <input type="hidden" id="team_idx" value="${team_idx}">
          </div>
        </div>
        <!-- calendar.js -->
        <div id="calendar"></div>
      </div>
      <!-- 소속 멤버 -->
      <div class="project-members">
      	<h1>● 소속 멤버</h1>
      	<div class="project-members-list">
      		<!-- TODO: fetch(멤버 리스트) -->
      	</div>
      </div>
      <script>
      	const memberList = document.getElementsByClassName('project-members-list')[0];
      	const teamIdx = document.getElementById('team_idx').value;
      	fetch(`/api/project/members/\${teamIdx}`)
      	.catch(error => console.error(error))
      	.then(response => response.json())
      	.then(data => {
      		memberList.innerHTML = '';
      		data.forEach(member => {
      			const memId = member.mem_id;
      			const memName = member.mem_name;
      			
      			const btn = document.createElement('button');
      			btn.classList.add = 'calendarMemberRender';
      			btn.textContent = memName;
      			
      			// TODO: btn.addEventListener('click', () => { calendar 렌더링 });
      			btn.addEventListener('click', () => {
      				// TODO: fetch GET 요청 보내서 멤버가 등록한 업무 리스트 가져오기
      				const url = `/api/project/members/work/list/\${memId}/\${teamIdx}`;
				     	fetch(url)
				     	.catch(error => console.error(error))
				     	.then(response => response.json())
				     	.then(data => {
				     		// TODO: 해당 달에 등록된 업무가 없는 경우
				     		const dataSize = data.length;
				     		if(dataSize <= 0) {
				     			alert(memName + ' 님은 해당 달에 등록된 업무가 없습니다.');
				     		}
				     		
				     		// TODO: Calendar 위쪽에 이름 렌더링
				     		const memNameEl = document.getElementById('member_name');
				     		memNameEl.textContent = memName + ' 업무';
				     		
				     		// TODO: Calendar 렌더링
				     		const calendar = document.getElementById('calendar');
				     		
				     		const calendarImpl = new FullCalendar.Calendar(calendar, {
				     			initialView : 'dayGridMonth',
				     			locale: 'ko',
				     			headerToolbar: {
				     				left: 'prev,next today',
				     				center: 'title'
				     			},
				     			height: 'calc(100% - 3rem)',
				     			eventDisplay: 'block',
				     			eventTimeFormat: {
				     				hour: '2-digit',
				     				minute: '2-digit',
				     				hour12: false
				     			},
				     			// TODO: 달력에 등록된 업무 막대기를 렌더링하는 함수
				     			events: function(fetchInfo, successCallback, failureCallback) {
				     				fetch('/api/project/members/work/list/jenits/1')
				     				.catch(err => console.err(err))
				     				.then(response => response.json())
				     				.then(data => {
				     					let events = data.map(item => ({
				     						start: item.works_sdate,
				     						end: item.works_edate,
				     						team_idx: item.team_idx
				     					}));
					     				successCallback(events);
				     				})
				     			},
				     			dateClick: function(info) {
				     				workAndDateRender(info);
				     			}
				     		});
				     		calendarImpl.render();
				     	});
      			});
      			memberList.appendChild(btn);
      		});
      	})
      	
      	function dateWorkRender(info) {
	   			const teamIdx = info.event.extendedProps.team_idx;
					const sdate = info.event.startStr;
					const edate = info.event.endStr;
					
					const url = `/api/project/work/list/\${teamIdx}/\${sdate}/\${edate}`;
					
   				fetch(url)
   				.catch(err => console.err(err))
   				.then(response => response.json())
   				.then(data => {
   					workRender(data);
   				});
				}
      </script>
      <script src="/js/keyStore.js"></script>
			<script src="/js/weather.js"></script>
			<script src="/js/conv_weather.js"></script>
      <!-- 날씨 Layer -->
      <div class="weather-container" id="weather-load">
      	<script>
      		init()
	      	// UI 다 만들어지면 주석 해제
	      	// 날씨 데이터 캐싱
      	</script>
				<div class="wrap">
					<div class="weather">
						<div>
							<div>
								<h1>오늘의 날씨</h1>
							</div>
							<div></div><!--주간 날씨보기 공공포털오류-->
						</div>
						<div>
						<div class="weather-icon"><img></div>
							<div class="weather-detail">
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
								<div class="item"></div>
							</div>
						</div>
					</div>
				</div>
      </div>

    </div>

    <!-- 업무 리스트 AJAX -->
    <div class="content col">

      <!-- 업무 리스트 Layer -->
      <div class="worklist-container">
        <div class="worklist-header">
          <div class="worklist-header-project-name">
            <p>TEAM / 귀멸의칼날</p>
          </div>
          <div class="worklist-header-day">
            <p id="clicked-date"></p>
          </div>
        </div>

        <div class="worklist-content-container">
          <!-- AM -->
          <div class="worklist-content">
            <div class="worklist-content-header">
              <p id="finFlagState"></p>
              <a id="workCreate" href="/work/create/">업무 등록</a>
            </div>
            <div class="worklist-content-main">
              <table id="worklist" class="worklist-content-table">
                <tr>
                  <th>작성자</th>
                  <th>수행 시작일</th>
                  <th>수행 종료일</th>
                  <th>업무 내용</th>
                  <th>알람 시간</th>
                  <th>비고</th>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>
</div>
<script>
  // 업무 등록 버튼 (a Tag)
	const workCreateLocation = document.getElementById('workCreate');
	workCreateLocation.addEventListener('click', (e) => {
		e.preventDefault();
		const teamIdx = document.getElementById('team_idx').value;
		const url = workCreateLocation.href;
		window.open(url + teamIdx, '_blank');
	});
</script>

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
              // (중요) 이 부분은 프로젝트의 JWT 저장 방식에 맞게 확인/수정
              'Authorization': 'Bearer ' + localStorage.getItem('calendarToken'') 
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
</body>
</html>