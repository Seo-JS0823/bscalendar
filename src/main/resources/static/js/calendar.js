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
    }
  });
  calendarImpl.render();
});
/*
FullCalendar 재 렌더링 반응형 속성 깨짐 현상 해결법

1. new FullCalendar.Calendar를 전역으로 뺀 후

옵션을 변경시 calendar.setOption(optionName, value)
이벤트데이터 변경시 calendar.removeAllEvents() 후 calendar.addEventSource(newEvents)
달력 새로고침 calendar.refetchEvents()
              calendar.destory()

 */