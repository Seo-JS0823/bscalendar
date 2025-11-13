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
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');

      const startDate = document.getElementById('startDate');
      startDate.value = `${year}-${month}-${day}`;

      const endDate = document.getElementById('endDate');
      endDate.value = `${year}-${month}-${day}`;

      const alramControl = document.getElementById('alram-control');
      alramControl.addEventListener('click', () => {
        const element = document.getElementById('alram');
        const descript = document.getElementById('alram-descript');
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

      const createBtn = document.getElementById('work-create-btn');
      createBtn.addEventListener('click', () => {
        alert('TODO: 등록 이벤트');
      });
    })
  </script>
</head>
<body>
<nav class="top-menu-area">

</nav>

<div class="container">
	<input type="hidden" value="${team_idx}">
  <div class="work-create-container">
    <div class="work-create-header">
      <h1>업무 등록</h1>
    </div>
    <div class="work-create-content">
      <div class="work-create-content-box">
        <div class="work-create-content-row">
          <input type="radio" name="WORKS_HIDE"><label for="WORKS_HIDE">공유 (팀 업무)</label>
          <input type="radio" name="WORKS_HIDE" checked><label for="WORKS_HIDE">비공유 (개인 업무)</label>
        </div>
        <hr>
        <div class="work-create-content-row gap1">
          <label class="work-create-content-label pointer" id="alram-control" for="alram">알람 설정</label>
          <input class="close" type="datetime-local" id="alram">
          <input class="open" type="text" id="alram-descript" placeholder="알람 설정을 눌러주세요." readonly>
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