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
<%@ include file="../nav.jsp" %>

<div class="container">
  <div class="projectList-area">
    <div class="projectList-container">
      <div class="projectList-content">
        <h1>Team Calendar List</h1>
        <h2>
        	<span style="color: #00aa00">● 진행중</span>
        	<span style="color: #dc3545">● 종료됨</span>
        </h2>
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
              const memberId = getTokenFromInfo('username');
              
              if(!projectName) {
            	  alert('프로젝트 제목은 필수입니다.')
            	  e.preventDefault();
              }
              const member = {
            		  team_name: projectName,
            		  team_sdate: projectStartDate,
            		  team_edate: projectEndDate,
            		  mem_id: memberId
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
	const mem_id = getTokenFromInfo('username');
	const url = '/api/project/' + mem_id;
	fetch(url)
	.catch(error => console.log(error))
	.then(response => response.json())
	.then(data => {
		// TODO: 프로젝트 리스트 렌더링
		const renderingTarget = document.getElementById('project-list-rendering');
	
		Array.from(data).forEach(project => {
			// TODO: innerHTML
			const team_idx = project.team_idx;
			const li = document.createElement('li');
			const team_con_flag = project.team_con_flag;
			
			// TODO: Member 초대, Project 수정 mouseup rendering
			const inviteAndUpdate = document.createElement('div');
			inviteAndUpdate.classList.add('inviteAndUpdate');
			inviteAndUpdate.style.display = 'none';
			if(team_con_flag === 'N') {
				li.classList.add('project-on');
			} else if(team_con_flag === 'Y') {
				li.classList.add('project-off');
			}
			
			// TODO inviteAndUpdate 버블링 방지
			inviteAndUpdate.addEventListener('click', (e) => {
				e.stopPropagation();
				e.preventDefault();
			});
			
			let innerHTML = '';
			if(mem_id === project.mem_id) {
				innerHTML = `
				<div class="master">
					<p>Team</p><h2>\${project.team_name}</h2>
					<p>인원</p><h2>\${project.member_count} 명</h2>
				</div>
				`;
			} else {
				innerHTML = `
				<div>
					<p>Team</p><h2>\${project.team_name}</h2>
					<p>인원</p><h2>\${project.member_count} 명</h2>
				</div>
				`;
			}
			
			//<p>권한자</p><h2>\${project.mem_name}</h2>
			innerHTML += `
			<div>
				<p>권한자</p><h2>\${project.mem_name}</h2>
			</div>
			<div>
				<p>기간</p><h2>\${project.team_sdate} ~ \${project.team_edate}</h2>
			</div>
			`;
			
			li.innerHTML = innerHTML;
			
			li.addEventListener('click', (e) => {
				// TODO: window.location.href = URL
				// TODO: '/project' URL을 받는 일반 컨트롤러에서 JWT 토큰에서 USERID를 추출해야하나??
				window.location.href = `/project/\${project.team_idx}`;
			});
			
			// TODO 멤버 초대 버튼
			// JWT 완성 후 MEM_ID 비교해서 if문으로 생성한 사람만 할당
			const inviteMember = document.createElement('div');
			inviteMember.classList.add('inviteClick');
			inviteMember.style.backgroundColor = '#00aa00';
			inviteMember.style.color = 'white';
			inviteMember.textContent = '멤버 설정';
			
			const inviteMemberModal = inviteMemberModalElement(team_idx);
			inviteMemberModal.classList.add('close');
			
			// TODO 멤버 초대 버튼 이벤트 핸들러 & 버블링 방지
			inviteMember.addEventListener('click', (e) => {
				e.stopPropagation();
				e.preventDefault();
				modalCloseOpen(inviteMemberModal);
			});
			
			li.addEventListener('mouseover', () => {
				inviteAndUpdate.style.display = 'flex';
				inviteAndUpdate.style.zIndex = 899;
			});
			
			// TODO 프로젝트 수정 버튼
			const projectUpdate = document.createElement('div');
			projectUpdate.classList.add('inviteClick');
			projectUpdate.style.backgroundColor = '#6495ed';
			projectUpdate.style.color = 'white';
			projectUpdate.textContent = '설정 변경';
			
			const projectSettingUpdateModal = projectSettingUpdateElement(team_idx);
			projectSettingUpdateModal.classList.add('close');
			
			// TODO 프로젝트 수정 버튼 이벤트 핸들러 & 버블링 방지
			projectUpdate.addEventListener('click', (e) => {
				e.stopPropagation();
				e.preventDefault();
				modalCloseOpen(projectSettingUpdateModal);
			});
			
			li.addEventListener('mouseout', () => {
				inviteAndUpdate.style.display = 'none';
			});
			
			if(mem_id === project.mem_id) {
				inviteAndUpdate.appendChild(inviteMember);
				inviteAndUpdate.appendChild(projectUpdate);
				li.appendChild(inviteAndUpdate);	
			}
				renderingTarget.appendChild(li);			
		});
	});
	
	function modalCloseOpen(element) {
		document.body.appendChild(element);
		const modalState = element.className.split(' ')[1];
		if(modalState === 'close') {
			element.classList.remove('close');
			element.classList.add('open');
		} else if(modalState === 'open') {
			element.classList.remove('open');
			element.classList.add('close');
			document.body.removeChild(element);
		}
	}
	
	function projectSettingUpdateElement(team_idx) {
		const projectSetting = document.createElement('div');
		projectSetting.classList.add('projectSetting');
		
		const projectSettingHeader = document.createElement('div');
		projectSettingHeader.classList.add('projectSettingHeader');
		projectSettingHeader.textContent = '프로젝트 설정';
		
		const projectSettingInput = document.createElement('div');
		projectSettingInput.classList.add('projectSettingInput');
			
			const settingBox1 = document.createElement('div');
			settingBox1.classList.add('settingBox');
			const labelProjectName = document.createElement('label');
			labelProjectName.textContent = '프로젝트 명';
			const inputProjectName = document.createElement('input');
			inputProjectName.type = 'text';
			settingBox1.appendChild(labelProjectName);
			
			const settingBox2 = document.createElement('div');
			settingBox2.classList.add('settingBox');
			const labelProjectStartDate = document.createElement('label');
			labelProjectStartDate.textContent = '프로젝트 시작 날짜';
			const inputProjectStartDate = document.createElement('input');
			inputProjectStartDate.type = 'date';
			inputProjectStartDate.setAttribute('readonly', true);
			settingBox2.appendChild(labelProjectStartDate);
			
			const settingBox3 = document.createElement('div');
			settingBox3.classList.add('settingBox');
			const labelProjectEndDate = document.createElement('label');
			labelProjectEndDate.textContent = '프로젝트 종료 날짜';
			const inputProjectEndDate = document.createElement('input');
			inputProjectEndDate.type = 'date';
			settingBox3.appendChild(labelProjectEndDate);
			
			const settingBox4 = document.createElement('div');
			settingBox4.classList.add('settingBox');
			const updateSettingBtn = document.createElement('button');
			updateSettingBtn.textContent = '저 장';
			updateSettingBtn.addEventListener('click', () => {
				// TODO: 변경 내용이 실제로 저장되도록 fetch 요청 patch
				const projectName = inputProjectName.value;
				const projectSdate = inputProjectStartDate.value;
				const projectEdate = inputProjectEndDate.value;
				
				const startDate = new Date(projectSdate);
				const endDate = new Date(projectEdate);
				
				// TODO: 종료 날짜가 시작 날짜보다 이르면 경고창
				if(startDate > endDate) {
					alert('종료 날짜를 시작 날짜 보다 이르게 설정할 수 없습니다.');
					return;
				}
				
				const projectUpdateUrl = `/api/project/setting/\${team_idx}/\${projectEdate}/\${projectName}`;
				const headers = { method: 'PATCH' }
				fetch(projectUpdateUrl, headers)
				.then(response => {
					return response.json();
				})
				.then(data => {
					
				});
				
			});
			const projectWorkEndBtn = document.createElement('button');
			projectWorkEndBtn.textContent = '프로젝트 완료';
			projectWorkEndBtn.addEventListener('click', () => {
				// TODO: 프로젝트가 종료되도록 fetch 요청 delete
				const projectWorkEndUrl = '/api/project/endProject/' + team_idx;
				const headers = { method: 'DELETE' }
				fetch(projectWorkEndUrl, headers)
				.then(response => response.json())
				.then(data => {
					alert(data.message);
					window.location.reload();
				});
			});
			/*
			const projectMasterUpdateBtn = document.createElement('button');
			projectMasterUpdateBtn.textContent = '관리자 변경';
			projectMasterUpdateBtn.addEventListener('click', () => {
				alert(team_idx);
			});
			*/
			const modalCloseBtn = document.createElement('button');
			modalCloseBtn.textContent = '닫 기';
			modalCloseBtn.addEventListener('click', () => {
				modalCloseOpen(projectSetting);
			});
			//settingBox4.appendChild(projectMasterUpdateBtn);
			settingBox4.appendChild(projectWorkEndBtn);
			settingBox4.appendChild(updateSettingBtn);
			settingBox4.appendChild(modalCloseBtn);
			
			
			
			// TODO: fetch API return 프로젝트 설정
			const settingDataUrl = `/api/project/setting/\${team_idx}`;
			fetch(settingDataUrl)
			.then(response => {
				const status = response.status;
				if(status === 204) {
					console.log(settingDataUrl + ' ' + status)
				}
				return response.json();
			})
			.then(data => {
				inputProjectName.value = data.project.team_name;
				settingBox1.appendChild(inputProjectName);
				
				inputProjectStartDate.value = data.project.team_sdate;
				settingBox2.appendChild(inputProjectStartDate);
				
				inputProjectEndDate.value = data.project.team_edate;
				settingBox3.appendChild(inputProjectEndDate);
			});
		projectSettingInput.appendChild(settingBox1);
		projectSettingInput.appendChild(settingBox2);
		projectSettingInput.appendChild(settingBox3);
		projectSettingInput.appendChild(settingBox4);
		
		projectSetting.appendChild(projectSettingHeader);
		projectSetting.appendChild(projectSettingInput);
		
		return projectSetting;
	}
	
	// 중복 렌더링 방지해야함 TODO
	function inviteMemberModalElement(team_idx) {
		const inviteMemberModal = document.createElement('div');
		inviteMemberModal.classList.add('inviteMember');
		
		// header
		const inviteMemberHeader = document.createElement('div');
		inviteMemberHeader.classList.add('inviteMember-header');
		inviteMemberHeader.textContent = '멤버 초대';
		inviteMemberModal.appendChild(inviteMemberHeader);
		const inviteModalCloseBtn = document.createElement('button');
		inviteModalCloseBtn.textContent = '닫 기';
		inviteModalCloseBtn.addEventListener('click', () => {
			modalCloseOpen(inviteMemberModal);
		});
		inviteMemberHeader.appendChild(inviteModalCloseBtn);
		
		// search input
		const inviteMemberSearch = document.createElement('div');
		inviteMemberSearch.classList.add('inviteMember-search')
		const searchDiv = document.createElement('div');
		const search = document.createElement('input');
		search.type = 'text';
		searchDiv.appendChild(search);
		
		const searchLabelDiv = document.createElement('div');
		const searchLabel = document.createElement('label');
		searchLabel.textContent = '검 색';
		searchLabelDiv.appendChild(searchLabel);
		
		const inviteLabelDiv = document.createElement('div');
		const inviteLabel = document.createElement('label');
		inviteLabel.textContent = '투 입';
		inviteLabelDiv.appendChild(inviteLabel);
		
		// search Response Binding
		const inviteMemberBinding = document.createElement('div');
		inviteMemberBinding.classList.add('inviteMember-binding');
		
		// bindingArea 첫 번째 = fetch로 불러온 바인딩 영역
		const inviteFetchToBind = document.createElement('div');
		inviteFetchToBind.classList.add('bindingArea');
		
		// bindingArea 두 번째 = 바인딩된 이름 클릭시 바인딩
		const inviteNameClickBind = document.createElement('div');
		inviteNameClickBind.classList.add('bindingArea');
		inviteNameClickBind.id = 'clicked-bindArea';
		
		const inviteNameHeader = document.createElement('div');
		inviteNameHeader.classList.add('sosocHeader');
		inviteNameHeader.textContent = '추가된 멤버';
		inviteNameClickBind.appendChild(inviteNameHeader);
		
		// bindingArea 세 번째 = 이미 프로젝트에 소속된 인원 표시
		const projectSosocMembers = document.createElement('div');
		projectSosocMembers.classList.add('bindingArea');
		projectSosocMembers.id = 'sosoc-bindArea';
		
		const sosocHeader = document.createElement('div');
		sosocHeader.classList.add('sosocHeader');
		sosocHeader.textContent = '소속 멤버';
		projectSosocMembers.appendChild(sosocHeader);
		
		const sosocUrl = '/api/project/members/' + team_idx;
		fetch(sosocUrl)
		.then(response => response.json())
		.then(data => {
			data.forEach(member => {
				const sosocName = document.createElement('div');
				sosocName.classList.add('clicked-name');
				sosocName.textContent = member.mem_name;
				sosocName.addEventListener('click', () => {
					// TODO 선택하고 confirm해서 확인누르면 추방 로직하고 reload
					
					if(confirm(member.mem_name + '님을 프로젝트에서 내보내겠습니까?')) {
						// TODO: fetch API -> 프로젝트에서 멤버 추방 기능 API 만들기
						// TODO: /api/project/member/del/{team_idx}/{mem_id} method: delete
						const memberDelUrl = '/api/project/member/del/' + team_idx + '/' + member.mem_id;
						const headers = { method: 'DELETE' }
						fetch(memberDelUrl, headers)
						.then(response => {
							const status = response.status;
							if(status === 400) {
								alert(response.json().message);
								window.location.reload();
							}
							return response.json();
						})
						.then(data => {
							const message = data.message;
							if(message) {
								alert(message);
							}
							window.location.reload();
						})
					} else {
						return;
					}
				});
				if(mem_id === member.mem_id) {}
				else {
					projectSosocMembers.appendChild(sosocName);
				}
			})
		});
		
		// TODO searchLabel Click Event fetch url /api/member/read/{mem_name}
		const bindEl = document.createElement('ul');
		bindEl.classList.add('memberBind');
		// TODO bindEl 초기값 세팅 fetch All members
		// TODO binding ClickEvent 함수로 빼기
		fetch('/api/project/member/read-all/' + team_idx)
		.catch(err => console.err(err))
		.then(response => response.json())
		.then(data => {
			let indexKey = 0;
			data.forEach(member => {
				const li = memberSearchBind(member, indexKey);
				bindEl.appendChild(li);
				indexKey++;
			});
		});
		
		searchLabel.addEventListener('click', (e) => {
			const memName = search.value;
			if(!memName) {
				e.stopPropagation();
				e.preventDefault();
				alert('검색어를 입력하세요.');
				return;
			}
			const url = `/api/project/member/read/\${memName}`;
			
			// TODO: inviteFetchToBind에 fetch로 불러온 값 바인딩
			fetch(url)
			.catch(err => console.err(err))
			.then(response => response.json())
			.then(data => {
				if(data.length < 1) {
					alert('검색된 정보가 존재하지 않습니다.');
					return;
				}
				bindEl.innerHTML = '';
				data.forEach(member => {
					const li = memberSearchBind(member);
					bindEl.appendChild(li);
				});
			});
		});
		
		inviteLabel.addEventListener('click', (e) => {
			// TODO: fetch 선택된 멤버 초대하기 여기에 TEAM_IDX 필요함.
			const clickedMembers = getClickedMembers();
			const members = {
				members: clickedMembers
			}
			// TODO: members 객체를 body에 담아서 프로젝트 투입 컨트롤러로 연결
			// TODO: URL /api/project/member/add
			
			if(confirm('선택하신 멤버를 투입하시겠습니까?')) {
				const url = '/api/project/member/add/' + team_idx;
				fetch(url, {
					method: 'post',
					headers: {
						'Content-Type':'application/json'
					},
					body: JSON.stringify(members)
				})
				.then(response => response.json())
				.then(data => {
					if(data.errorMessage) {
						alert(data.errorMessage);
						return;
					};
					if(data.successMessage) {
						alert(data.successMessage);
						window.location.reload();
					}
				});
			} else {
				e.preventDefault();
				return;
			}
		});
		
		inviteMemberSearch.appendChild(searchDiv);
		inviteMemberSearch.appendChild(searchLabelDiv);
		inviteMemberSearch.appendChild(inviteLabelDiv);
		
		
		inviteFetchToBind.appendChild(bindEl);
		inviteMemberBinding.appendChild(inviteFetchToBind);
		inviteMemberBinding.appendChild(inviteNameClickBind);
		inviteMemberBinding.appendChild(projectSosocMembers);
		inviteMemberModal.appendChild(inviteMemberSearch);
		inviteMemberModal.appendChild(inviteMemberBinding);
		
		return inviteMemberModal;
	}
	
	function memberSearchBind(member, indexKey) {
		const nameEl = document.createElement('p');
		nameEl.textContent = member.mem_name;
		const posiEl = document.createElement('p');
		posiEl.textContent = member.mem_position;
		const partEl = document.createElement('p');
		partEl.textContent = member.mem_depart;
		const idEl = member.mem_id;
		
		const li = document.createElement('li');
		li.setAttribute('data-role', 'no');
		const uuid = indexKey;
		li.setAttribute('data-id', uuid)
		li.appendChild(nameEl);
		li.appendChild(posiEl);
		li.appendChild(partEl);
		
		// 멤버 추가, 취소 동시에
		li.addEventListener('click', () => {
			const role = li.getAttribute('data-role');
			const bindArea = document.getElementById('clicked-bindArea');
			if(role === 'no') {
				li.setAttribute('data-role', 'ok');
				li.style.border = '2px solid #6495ed';
				li.style.boxShadow = '0 0 3px #ccc';
				
				// TODO 파라미터에 바인딩할 영역 추가하고 클릭시 바인딩
				const bindName = document.createElement('div')
				bindName.classList.add('clicked-name');
				
				const clicked_uuid = li.getAttribute('data-id');
				bindName.setAttribute('data-id', clicked_uuid);
				bindName.setAttribute('data-member', encodingMemId(idEl));
				bindName.textContent = nameEl.textContent;
				
				// TODO 바인딩된 이름도 클릭시 삭제되게
				bindName.addEventListener('click', () => {
					const binding_uuid = bindName.getAttribute('data-id');
					const mappered_uuid = li.getAttribute('data-id');
					if(binding_uuid === mappered_uuid) {
						li.setAttribute('data-role', 'no');
						li.style.border = '1px solid #ccc';
						li.style.boxShadow = 'none';
						bindArea.removeChild(bindName);
					}
				});
				bindArea.appendChild(bindName);
				
			} else if(role === 'ok') {
				li.setAttribute('data-role', 'no');
				li.style.border = '1px solid #ccc';
				li.style.boxShadow = 'none';
				
				const bindAreaChildren = bindArea.children;
				Array.from(bindAreaChildren).forEach(member => {
					const target_uuid = li.getAttribute('data-id');
					const clicked_uuid = member.getAttribute('data-id');
					if(target_uuid === clicked_uuid) {
						bindArea.removeChild(member);
					}
				})
			}
		});
		
		return li;
	}
	
	// member_id encoding
	function encodingMemId(mem_id) {
		const utf8 = encodeURIComponent(mem_id).replace(/%([0-9A-F]{2})/g,
			function(match, p1) {
				return String.fromCharCode('0x' + p1);
			}
		);
		return btoa(utf8);
	}
	
	// member_id decoding
	function decodingMemId(mem_id) {
		const binary = atob(mem_id);
		const utf8 = binary.split('').map(function(c) {
			return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
		}).join('');
		return decodeURIComponent(utf8);
	}
	
	// 배열 리턴
	function getClickedMembers() {
		const bindArea = document.getElementById('clicked-bindArea');
		const bindedMembers = Array.from(bindArea.children);
		const members = [];
		for(let i = 1; i < bindedMembers.length; i++) {
			const binaryMemName = bindedMembers[i].getAttribute('data-member');
			const memName = decodingMemId(binaryMemName);
			members.push(memName);			
		}
		if(members.length < 1) {
			alert('투입할 직원을 선택하세요.');
			return;
		}
		return members;
	}
</script>
</body>
</html>