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
      minute: '2-dight',
      hour12: false
    }
  });
  calendarImpl.render();
});