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
  <div class="projectList-area">
    <div class="projectList-container">
      <div class="projectList-content">
        <h1>Team Calendar List</h1>
        <h2><span style="color: #00aa00">● 진행중</span> <span style="color: #dc3545">● 종료됨</span></h2>
        <button id="project-create-btn">프로젝트 생성</button>
        <br>
        <div id="project-list">
          <p>Scroll</p>
          <ul>
            <li class="project-off">
              <div>
                <p>Team</p><h2>루피 해적단</h2>
                <p>인원</p><h2>5명</h2>
              </div>
              <div>
                <p>권한자</p><h2>서주성</h2>
              </div>
              <div>
                <p>기간</p><h2>2025-11-04 ~ 2026-01-24</h2>
              </div>
            </li>
          </ul>
          <div id="project-create" data-role="close">
            <h1>프로젝트 생성</h1>
            <hr>
            <div class="create-input">
              <label>프로젝트 제목</label>
              <input type="text" id="team_name" placeholder="프로젝트 제목을 입력하세요.">
            </div>
            <div class="create-input">
              <label>시작 날짜</label>
              <input type="date" id="team_sdate">
              <label>종료 날짜</label>
              <input type="date" id="team_edate">
            </div>
            <div class="create-btns">
              <button id="create-btn">CREATE</button>
              <button id="create-btn-close">CLOSE</button>
            </div>
          </div>
          <script>
            const createBox = document.getElementById('project-create-btn');
            createBox.addEventListener('click', () => {
              const projectArea = document.getElementById('project-create');
              const state = projectArea.getAttribute('data-role');
              
              if(state === 'close') {
                projectArea.setAttribute('data-role', 'open');
                projectArea.style.display = 'block';
              }
            });

            const createBtn = document.getElementById('create-btn');
            createBtn.addEventListener('click', (e) => {
              const url = '/api/project';
              
              const projectName = document.getElementById('team_name').value;
              const projectStartDate = document.getElementById('team_sdate').value;
              const projectEndDate = document.getElementById('team_edate').value;
              
              if(!projectName) {
            	  alert('프로젝트 제목은 필수입니다.')
            	  e.preventDefault();
              }
              const member = {
            		  team_name: projectName,
            		  team_sdate: projectStartDate,
            		  team_edate: projectEndDate
              }
              console.log(member);
              fetch(url, {
            	  method: 'post',
            	  headers: {
            		  'Content-Type':'application/json'
            	  },
            	  body: JSON.stringify(member)
              })
              .catch(error => console.log(error))
              .then(response => response.json())
              .then(data => {
            	  
              });
              
            });

            const closeBtn = document.getElementById('create-btn-close');
            closeBtn.addEventListener('click', () => {
              const projectArea = document.getElementById('project-create');
              const state = projectArea.getAttribute('data-role');
              
              if(state === 'open') {
                projectArea.setAttribute('data-role', 'close');
                projectArea.style.display = 'none';
              }
            });
          </script>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>