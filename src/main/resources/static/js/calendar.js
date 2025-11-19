document.addEventListener('DOMContentLoaded', function() {
	const memberId = getTokenFromInfo('username');
  const calendar = document.getElementById('calendar');
  
  // 달력에 날짜가 클릭됨을 저장하는 변수
  let clickedDayEl = null;
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
			const startDate = fetchInfo.startStr.substring(0, 10);
			const endDate = fetchInfo.endStr.substring(0, 10);
			const nowDate = getNowDateString();
			
			fetch(`/api/project/work/cal/${startDate}/${endDate}/${teamIdx}/${memberId}`)
			.catch(err => console.err(err))
			.then(response => response.json())
			.then(data => {
				let events = workDataSetting(data);
				const mergedEvents = mergeEvents(events);
 				successCallback(mergedEvents);

 				fetch(`/api/project/work/cal/${nowDate}/${teamIdx}/${memberId}`)
 				.then(response => response.json())
 				.then(data => {
					workRender(data);
				})
 				
			})
		},
		dateClick: function(info) {
			if(clickedDayEl) {
				clickedDayEl.classList.remove('fc-day-today');
				clickedDayEl.classList.remove('clicked-day');
			}
			info.dayEl.classList.add('clicked-day');
			
			clickedDayEl = info.dayEl;
			workAndDateRender(info);
		}
  });
  const nowDateEl = document.getElementById('clicked-date');
	nowDateEl.textContent = getNowDateString();
  calendarImpl.render();
});

const memberTokenId = getTokenFromInfo('username');

function endDatePlusOne(endStr) {
	const oldDate = new Date(endStr);
	oldDate.setDate(oldDate.getDate() + 1);
	
	const year = oldDate.getFullYear();
	const month = String(oldDate.getMonth() + 1).padStart(2, '0');
	const day = String(oldDate.getDate()).padStart(2, '0');
	
	return `${year}-${month}-${day}`;
}

function workDataSetting(data) {
	let events = data.map(item => ({
		start: item.works_sdate.substring(0, 10),
		end: endDatePlusOne(item.works_edate.substring(0, 10)),
		team_idx: item.team_idx,
		hideFlag: item.works_hide
	}));
	
	events.forEach(item => {
		const hideFlag = item.hideFlag;
		if(hideFlag === 'N') {
			item.title = '팀 업무';
			item.backgroundColor = 'lightpink';
			item.color = 'lightpink';
		} else if(hideFlag === 'Y') {
			item.title = '개인 업무';
			item.backgroundColor = '#6495ed';
			item.color = '#6495ed';
		}
	});
	return events;
}

function workRender(workInfo) {
	// TODO: 업무 리스트 렌더링
	const worklistEl = document.getElementById('worklist');
	const worklisthideEl = document.getElementById('worklisthide');
	worklisthideEl.innerHTML = `
	<tr>
    <th>작성자</th>
    <th>수행 시작일</th>
    <th>수행 종료일</th>
    <th>업무 내용</th>
    <th>알람 시간</th>
    <th>비고</th>
  </tr>
	`;
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
	// TODO: 미완료, 완료 상태 렌더링
	const finFlagState = document.getElementById('finFlagState');
	let notFinFlag = 0;
	let okFinFlag = 0;
	
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
			notFinFlag++;
			eventTd.classList.add('worklist-notComplete');
			eventTd.textContent = '미완료';
			// TODO: 미완료 상태일 때 이벤트 핸들러 등록
			eventTd.addEventListener('click', (e) => {
				if(confirm('업무를 완료 처리 하시겠습니까?')) {
					e.stopPropagation();
					e.preventDefault();
					
					// TODO: 완료 처리하는 컨트롤러와 로직
					const url = `/api/work/update/${work.works_idx}`;
					
					/*
					fetch(url, { method: 'put' })
					.catch(err => console.err(err))
					.then(response => response.json())
					.then(data => {
						
					});
					*/
				} else {
					e.stopPropagation();
					e.preventDefault();
				}
			})
			
		} else if (finFlag === 'Y') {
			okFinFlag++;
			eventTd.classList.add('worklist-complete');  				
			eventTd.textContent = '완료';
		}
		tr.appendChild(eventTd);
		
		tr.addEventListener('click', () => {
			// TODO: work-detail location
			const url = '/work/detail/' + works_idx;
			window.location.href = url;
		});
		const hideState = work.works_hide;
		if(hideState === 'N') {
			worklistEl.appendChild(tr);
		} else if(hideState === 'Y') {
			worklisthideEl.appendChild(tr);
		}
		
	});
	finFlagState.textContent = `미완료 업무 : ${notFinFlag} / 완료 업무 : ${okFinFlag}`;
}

// 막대기 렌더링 날짜 병합 함수
function mergeEvents(events) {
	console.log(events);
	if(!events || events.length === 0) {
		return [];
	}
	
	// 이벤트 시작 날짜 기준 오름차순 정렬
	events.sort((a, b) => new Date(a.start) - new Date(b.start));
	
	const merged = [];
	
	// 병합을 위해 이벤트 복사
	let currentEvent = { ...events[0] };
	for(let i = 1; i < events.length; i++) {
		const nextEvent = events[i];
		
		const currentEnd = new Date(currentEvent.end);
		const nextStart = new Date(nextEvent.start);
		
		// 현재 이벤트의 종료 날짜가 다음 이벤트의 시작 날짜보다 늦을 경우
		if(currentEnd > nextStart) {
			
			// 병합: 더 늦은 종료날짜로 현재 이벤트의 종료일을 갱신
			const nextEnd = new Date(nextEvent.end);
			if(nextEnd > currentEnd) {
				currentEvent.end = nextEvent.end;
			}
		} else {
			merged.push(currentEvent);
			currentEvent = { ...nextEvent };
		}
	}
	merged.push(currentEvent);
	return merged;
}

/* 날짜, 미완료, 완료업무 렌더링 */
function workAndDateRender(info) {
	const teamIdx = window.location.pathname.replace('/project/', '');
	const date = info.dateStr;
	const url = `/api/project/work/cal/${date}/${teamIdx}/${memberTokenId}`;
	
	fetch(url)
	.catch(err => console.err(err))
	.then(response => {
		const status = response.status;
		if(status === 204) {
			const worklistNoContent = document.getElementById('worklist');
			worklistNoContent.innerHTML = `
			<tr>
			  <th>해당 날짜에 조회된 공유 업무가 존재하지 않습니다.</th>
			</tr>
			`;
			const worklisthideNoContent = document.getElementById('worklisthide');
			worklisthideNoContent.innerHTML = `
			<tr>
			  <th>해당 날짜에 조회된 개인 업무가 존재하지 않습니다.</th>
			</tr>
			`;
			const finFlagStateNoContent = document.getElementById('finFlagState');
			finFlagStateNoContent.innerHTML = '';
			
			const clickedDateNoContent = document.getElementById('clicked-date');
			clickedDateNoContent.innerHTML = date;
			return;
		}
		return response.json();
	})
	.then(data => {
		if(!data) {
			return;
		}
		const clickedDate = document.getElementById('clicked-date');
		clickedDate.textContent = date;
		workRender(data);
	});
}

/* 오늘 날짜 문자열 반환 YYYY-MM-DD */
function getNowDateString() {
	const now = new Date();
	const year = now.getFullYear();
	const month = String(now.getMonth() + 1).padStart(2, '0');
	const day = String(now.getDate()).padStart(2, '0');
	const date = `${year}-${month}-${day}`;
	
	return date;
}
/*
FullCalendar 재 렌더링 반응형 속성 깨짐 현상 해결법

1. new FullCalendar.Calendar를 전역으로 뺀 후

옵션을 변경시 calendar.setOption(optionName, value)
이벤트데이터 변경시 calendar.removeAllEvents() 후 calendar.addEventSource(newEvents)
달력 새로고침 calendar.refetchEvents()
              calendar.destory()

 */