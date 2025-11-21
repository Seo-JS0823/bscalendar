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

/*
// 막대기 렌더링 날짜 병합 함수
function mergeEvents(events) {
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
		const hideFlag = events[i].hideFlag;
		
		// hideFlag = 'Y' IF hideFlag = 'N'
		// 'Y' = #6495ed AND 'N' = lightpink
		
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
*/
function mergeEvents(events) {
	if(!events || events.length === 0) {
		return [];
	}
	
	// 이벤트 시작 날짜 기준 오름차순 정렬
	events.sort((a, b) => new Date(a.start) - new Date(b.start));
	
  const hideMerged = [];
  const noHideMerged = [];
  for(let i = 0; i < events.length; i++) {
		const hideFlag = events[i].hideFlag;
		if(hideFlag === 'Y') {
			hideMerged.push(events[i]);
		} else if(hideFlag === 'N') {
			noHideMerged.push(events[i]);
		}
	}
	
	hideMerged.sort((a, b) => new Date(a.start) - new Date(b.start));
	noHideMerged.sort((a, b) => new Date(a.start) - new Date(b.start));
	
	const merged = [];
	
	let currentHideEvent = { ...hideMerged[0] };
	for(let i = 1; i < hideMerged.length; i++) {
		const nextEvent = hideMerged[i];
		const currentEnd = new Date(currentHideEvent.end);
		const nextStart = new Date(nextEvent.start);
		
		if(currentEnd > nextStart) {
			const nextEnd = new Date(nextEvent.end);
			if(nextEnd > currentEnd) {
				currentHideEvent.end = nextEvent.end;
			}
		} else {
			merged.push(currentHideEvent);
			currentHideEvent = { ...nextEvent };
		}
	}
	merged.push(currentHideEvent);
	
	let currentNoHideEvent = { ...noHideMerged[0] };
	for(let i = 1; i < noHideMerged.length; i++) {
		const nextEvent = noHideMerged[i];
		const currentEnd = new Date(currentNoHideEvent.end);
		const nextStart = new Date(nextEvent.start);
		
		if(currentEnd > nextStart) {
			const nextEnd = new Date(nextEvent.end);
			if(nextEnd > currentEnd) {
				currentHideEvent.end = nextEvent.end;
			}
		} else {
			merged.push(currentNoHideEvent);
			currentNoHideEvent = { ...nextEvent };
		}
	}
	merged.push(currentNoHideEvent);
	return merged;
}

/* 날짜, 미완료, 완료업무 렌더링 */
function workAndDateRender(info) {
	const teamIdx = window.location.pathname.replace('/project/', '');
	const date = info.dateStr;
	const url = `/api/project/work/cal/${date}/${teamIdx}/${memberTokenId}`;
	const finFlagState = document.getElementById('finFlagState');
	let yFlag = 0;
	let nFlag = 0;
	
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
			finFlagState.innerHTML = '';
			
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