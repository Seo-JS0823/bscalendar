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
          <ul id="project-list-rendering">
            
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
            		  team_edate: projectEndDate,
            		  mem_id: 'inotske'
              }
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
            	  console.log(data);
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

<script> // 로그인한 유저의 프로젝트 리스트 렌더링
	const url = '/api/project/inotske';
	fetch(url)
	.catch(error => console.log(error))
	.then(response => response.json())
	.then(data => {
		// TODO: 프로젝트 리스트 렌더링
		const renderingTarget = document.getElementById('project-list-rendering');
	
		Array.from(data).forEach(project => {
			// TODO: innerHTML
			
			const li = document.createElement('li');
			const team_con_flag = project.team_con_flag;
			
			if(team_con_flag === 'N') {
				li.classList.add('project-on')
			} else if(team_con_flag === 'Y') {
				li.classList.add('project-off')
			}
			
			const innerHTML = `
				<div>
					<p>Team</p><h2>\${project.team_name}</h2>
					<p>인원</p><h2>\${project.member_count} 명</h2>
				</div>
				<div>
					<p>권한자</p><h2>\${project.mem_name}</h2>
				</div>
				<div>
					<p>기간</p><h2>\${project.team_sdate} ~ \${project.team_edate}</h2>
				</div>
			`;
			
			li.innerHTML = innerHTML;
			
			li.addEventListener('click', () => {
				// TODO: window.location.href = URL
				// TODO: '/project' URL을 받는 일반 컨트롤러에서 JWT 토큰에서 USERID를 추출해야하나??
				window.location.href = `/project/\${project.team_idx}`;
			})
			
			renderingTarget.appendChild(li);
			
		});
	});
</script>
</body>
</html>