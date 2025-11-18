<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="/css/reset.css">
  <link rel="stylesheet" href="/css/layout.css">
  <title>부성카렌다</title>
  <script> // Alram Date
    document.addEventListener('DOMContentLoaded', function() {
    	const mem_id = getTokenFromInfo('username');
    	console.log(mem_id);
    	
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');

      const startDate = document.getElementById('startDate'); // 업무시작 날짜
      startDate.value = `${year}-${month}-${day}`;

      const endDate = document.getElementById('endDate'); // 업무 종료날짜
      endDate.value = `${year}-${month}-${day}`;

      const alarmControl = document.getElementById('alarm-control'); // 알람 설정 버튼
      
  	  // 알람설정 버튼 누르면 날짜선택 뜸 아니면 placeholder내용 뜸
      alarmControl.addEventListener('click', () => { 
        const element = document.getElementById('alarm');
        const descript = document.getElementById('alarm-descript');
        const state = element.className;
        console.log(state)
        if(state === 'close') {
          element.value = '';
          element.classList.remove('close');
          element.classList.add('open');
          descript.classList.remove('open');
          descript.classList.add('close');
        } else if (state === 'open') {
          element.value = '';
          element.classList.remove('open');
          element.classList.add('close');
          descript.classList.remove('close');
          descript.classList.add('open');
        }
      });

      const createBtn = document.getElementById('work-create-btn'); // 등록 버튼
      createBtn.addEventListener('click', () => {
    	  
    	  const openEl    		 = document.querySelector('#open'); // 공개 버튼
    	  const privateEl 		 = document.querySelector('#private'); // 비공개 버튼
    	  const alarmControlEl = document.querySelector('#alarm-control') // 알람 설정
    	  const alarmEl 			 = document.querySelector('#alarm'); // 알람 날짜
    	  const startDateEl 	 = document.querySelector('#startDate') // 업무 시작일
    	  const endDateEl 		 = document.querySelector('#endDate') // 업무 종료일
    	  const workCommentEl  = document.querySelector('#work-comment') // 업무 내용
    	  
    	  const alarmDescript = document.getElementById('alarm-descript');
    	  
    	  const teamIdx = ${team_idx}; // Model 에서 보내준 값
    	  const memId   = '${mem_id}'; // Model 에서 보내준 값
    	  
    	  const url = '/api/work/insertWork';
    	  
    	  let hideCheck  = 'N';
    	  let alarmCheck = 'N'
    	  
    	  if(alarmEl.value) {
    		  alarmCheck = 'Y';
    	  } 
    	  
    	  if(privateEl.checked) {
    		  hideCheck = 'Y';
    	  }

    	  fetch(url, {
    		  method: 'POST',
    		  headers: {'Content-type':'application/json; charset=UTF-8'},
    		  body: JSON.stringify({
    			  team_idx: teamIdx,
    		  	mem_id: memId,
    			  works_comment: workCommentEl.value,
    			  works_hide: hideCheck,
    			  works_arlam: alarmCheck,
    			  works_arlam_date: alarmEl.value,
    			  works_sdate: startDateEl.value, 
    				works_edate: endDateEl.value,
    		  }),
    	  })
    	  .then( response => {
    		  const status = response.status;
    		  if(status === 400) {
    			  alert('네트워크 오류로 인해 업무 등록이 실패하였습니다. 다시 시도해주세요.');
    			  return;
    		  }
    		  return response.json();
    	  })
    	  .then( data => {
    		  if(data.status === 'ok')
    				window.location.href = data.redirectUrl;
    	  })
    	  
      }); // 업무등록 클릭 이벤트
    }) // DOMContentLoaded
  </script>
</head>
<body>
<%@ include file="../nav.jsp" %>

<div class="container">
	<input type="hidden" value="${team_idx}">
  <div class="work-create-container">
    <div class="work-create-header">
      <h1>업무 등록</h1>
    </div>
    <div class="work-create-content">
      <div class="work-create-content-box">
        <div class="work-create-content-row">
          <label for="open"><input type="radio" id="open" name="alarm" value="">공개 (팀 공개)</label>
          <label for="private"><input type="radio" id="private" name="alarm" value="">비공개 (개인)</label>
        </div>
        <hr>
        <div class="work-create-content-row gap1">
          <label class="work-create-content-label pointer" id="alarm-control" for="alarm">알람 설정</label>
          <input class="close" type="datetime-local" id="alarm">
          <input class="open" type="text" id="alarm-descript" placeholder="알람 설정을 눌러주세요." readonly>
        </div>
        <div class="work-create-content-row mg1 gap1">
          <label class="work-create-content-label" for="startDate">업무 시작날짜</label>
          <input type="date" id="startDate">
          <label class="work-create-content-label" for="endDate">업무 종료날짜</label>
          <input type="date" id="endDate">
        </div>
        <hr>
        <div class="work-create-content-col gap1">
          <label class="work-create-content-label">업무 내용</label>
          <textarea class="work-content" id="work-comment"></textarea>
        </div>
        <div class="work-create-content-row">
          <button id="work-create-btn">등록</button>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>