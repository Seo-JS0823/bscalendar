document.addEventListener('DOMContentLoaded', function() {
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
    events: function(fetchInfo, successCallback, failureCallback) {
			// TODO: team_idx와 오늘날짜로 업무 리스트 요청하기
			const now = new Date();
			const year = now.getFullYear();
			const month = String(now.getMonth() + 1).padStart(2, '0');
			const day = String(now.getDate()).padStart(2, '0');
			const date = `${year}-${month}-${day}`;
			
			fetch(`/api/work/list/date/${date}/` + teamIdx)
			.catch(err => console.err(err))
			.then(response => response.json())
			.then(data => {
				console.log(data)
				let events = data.map(item => ({
					start: item.works_sdate.substring(0, 10),
					end: item.works_edate.substring(0, 10),
					team_idx: item.team_idx,
					hideFlag: item.works_hide
				}));
				// color 설정
				events.forEach(item => {
					const hideFlag = item.hideFlag;
					if(hideFlag === 'N') {
						item.display = 'none';
					} else if(hideFlag === 'Y') {
						item.title = '개인 업무';
						item.backgroundColor = '#6495ed';
						item.color = '#6495ed';
					}
				})
 				successCallback(events);
			})
		},
		dateClick: function(info) {
			// --> 날짜, team_idx로 worklist 가져오고 렌더링 하는것
			const teamIdx = window.location.pathname.replace('/project/', '');
			const date = info.dateStr;
			const url = `/api/work/list/date/${date}/` + teamIdx;
			
			fetch(url)
			.catch(err => console.err(err))
			.then(response => response.json())
			.then(data => {
				const clickedDate = document.getElementById('clicked-date');
				clickedDate.textContent = date;
				
				const finFlagState = document.getElementById('finFlagState');
				let notFinFlag = 0;
				let okFinFlag = 0;
				data.forEach(work => {
					const finFlag = work.works_fin_flag;
					if(finFlag === 'N') {
						notFinFlag++;
					} else if(finFlag === 'Y') {
						okFinFlag++;				     							
					}
				})
				finFlagState.textContent = `미완료 업무 : ${notFinFlag} / 완료 업무 : ${okFinFlag}`;
				
				// TODO: 오른쪾 업무 리스트 렌더링
				workRender(data);
			});
		}
  });
  calendarImpl.render();
});

function workRender(workInfo) {
	// TODO: 업무 리스트 렌더링
	const worklistEl = document.getElementById('worklist');
	worklistEl.innerHTML = `
	<tr>
    <th>작성자</th>
    <th>수행 시작일</th>
    <th>수행 종료일</th>
    <th>업무 내용</th>
    <th>알람 시간</th>
    <th>비고</th>
  </tr>
	`;
	Array.from(workInfo).forEach(work => {
		const works_idx = work.works_idx;
		const memName = work.mem_name;
		const sdate = work.works_sdate.substring(0, 10);
		const edate = work.works_edate.substring(0, 10);
		const comment = work.works_comment;
		const alramState = work.works_alram;
		let alramDate = '알람 미등록';
		if(alramState === 'Y') {
			alramDate = work.works_alram_date;
		}
		const tr = document.createElement('tr');
		
		const innerHTML = `
		<td>${memName}</td>
		<td>${sdate}</td>
		<td>${edate}</td>
		<td>${comment}</td>
		<td>${alramDate}</td>
		`;
		
		tr.innerHTML = innerHTML;
		
		const eventTd = document.createElement('td');
		
		// 완료 미완료 상태
		const finFlag = work.works_fin_flag;
		
		if (finFlag === 'N') {
			eventTd.classList.add('worklist-notComplete');
			eventTd.textContent = '미완료';
			// TODO: 미완료 상태일 때 이벤트 핸들러 등록
			eventTd.addEventListener('click', (e) => {
				if(confirm('업무를 완료 처리 하시겠습니까?')) {
					// TODO: 완료 처리하는 컨트롤러와 로직
					const url = `/api/work/update/${work.works_idx}`;
					console.log(url);
					
					/*
					fetch(url, { method: 'put' })
					.catch(err => console.err(err))
					.then(response => response.json())
					.then(data => {
						
					});
					*/
				} else {
					e.preventDefault();
				}
			})
			
		} else if (finFlag === 'Y') {
			eventTd.classList.add('worklist-complete');  				
			eventTd.textContent = '완료';
		}
		
		tr.appendChild(eventTd);
		
		tr.addEventListener('click', () => {
			// TODO: work-detail location
			const url = '/work/detail/' + works_idx;
			window.location.href = url;
		});
		
		worklistEl.appendChild(tr);
	});
}
/*
FullCalendar 재 렌더링 반응형 속성 깨짐 현상 해결법

1. new FullCalendar.Calendar를 전역으로 뺀 후

옵션을 변경시 calendar.setOption(optionName, value)
이벤트데이터 변경시 calendar.removeAllEvents() 후 calendar.addEventSource(newEvents)
달력 새로고침 calendar.refetchEvents()
              calendar.destory()

 */