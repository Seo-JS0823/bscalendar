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
</head>
<body>
<nav class="top-menu-area">

</nav>

<div class="container">
  <div class="detail-container">
    <div class="detail-descript">
    	<div class="row-box">
    		<h1>소속 프로젝트</h1>
    		<p>이노스케팀</p>
    	</div>
    	<div class="row-box">
    		<h1>등록일</h1>
    		<p>2025-11-08</p>
    	</div>
      <div class="row-box">
        <h1>작성자</h1>
        <p>서주성</p>
      </div>
    </div>

    <div class="detail-content">
    	<div class="detail-header-area">
	      <p class="detail-header" id="detail-update">
	      	업무 설정 △
	      	<span id="detail-update-descript" style="font-size: 1.2rem;" >
	      		설정을 변경하시려면 업무 설정을 누르세요.
	      	</span>
	      </p>
      </div>
      <div id="detail-update-area" class="opacityClose">
	      <div class="detail-content-check">
	        <input type="radio" name="works_hide"><label>공유 (팀 업무)</label>
	        <input type="radio" name="works_hide"><label>비공유 (개인 업무)</label>
	      </div>
	      <div class="detail-alram">
	      	<button id="setAlramBtn">알람 설정</button>
	      	<input id="setAlram" type="datetime-local" class="detail-setAlram close">
	      	<script>
	      		const detailUpdate = document.getElementById('detail-update');
	      		let btnState = false;
	      		detailUpdate.addEventListener('click', () => {
	      			const detailUpdateArea = document.getElementById('detail-update-area');
	      			const state = detailUpdateArea.className;
    					const detailHeaderArea = document.getElementsByClassName('detail-header-area')[0];
    					
	      			if(state === 'opacityClose') {
	      				detailUpdate.innerHTML = `업무 설정 ▽`;
	      				
	      				detailUpdateArea.classList.remove('opacityClose');
	      				detailUpdateArea.classList.add('opacitOpen');
	      				
	      				const updateBtn = document.createElement('button');
	      				updateBtn.addEventListener('click', () => {
	      					// TODO: 업무 설정 변경 로직
	      					
	      				});
	      				updateBtn.classList.add('work-updateBtn');
	      				updateBtn.textContent = '변경사항 저장';
	      				
      					detailHeaderArea.appendChild(updateBtn);
	      				
	      			} else if(state === 'opacitOpen') {
	      				const updateBtn = document.getElementsByClassName('work-updateBtn')[0];
	      				detailHeaderArea.removeChild(updateBtn);
	      				detailUpdate.innerHTML = `
	      				업무 설정 △
	      				<span id="detail-update-descript" style="font-size: 1.2rem;" >
      	      		설정을 변경하시려면 업무 설정을 누르세요.
      	      	</span>
	      				`;
	      				detailUpdateArea.classList.remove('opacitOpen');
	      				detailUpdateArea.classList.add('opacityClose');
	      			}
	      		});
	      	
	      		const setAlramBtn = document.getElementById('setAlramBtn');
	      		setAlramBtn.addEventListener('click', () => {
	      			const setAlram = document.getElementById('setAlram');
	      			const state = setAlram.className.split(' ')[1];
	      			if(state === 'close') {
	      				setAlram.value = undefined;
	      				setAlram.classList.remove('close');
	      				setAlram.classList.add('open');
	      			} else if(state === 'open') {
	      				setAlram.classList.remove('open');
	      				setAlram.classList.add('close');      				
	      			}
	      		});
	      	</script>
	      </div>
      </div>
      <p class="detail-header">업무 내용</p>
      <textarea class="detail-textarea">TODO: 작성자 본인이 들어오면 수정 / 삭제 / 알람 / 등을 설정할 수 있는 BOX 보이게</textarea>
      <p class="detail-header">업무 메모</p>
      <div class="work-reply-area">
      	<div class="col-box">
      		<p><span class="reply-name">이노스케</span> 2025-11-06 11:51</p>
	      	<div class="row-box">
		      	<div class="work-reply-member">
		      		<p>홍규형 잔다잔다 또잔다... 내가...왕이 될 상인가? 노는게 제일 좋아 친구들 모여라 언제나 즐거워 개구쟁이 뽀로로... 롤리폴리 롤리롤리 폴리</p>
		      	</div>
		      	<div class="work-reply-update">
		      		<button>수정</button>
		      		<button>삭제</button>
		      	</div>
	      	</div>
	      </div>
	    </div>
      <div class="work-reply">
				<textarea id="reply-input"></textarea>
				<button>메모 등록</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>