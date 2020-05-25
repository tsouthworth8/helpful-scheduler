<template>
  <div class="schedule">
    <FullCalendar
      class="calendar"
      ref="fullCalendar"
      defaultView="timeGridWeek"
      :header="{
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      }"
      :plugins="calendarPlugins"
      :events="calendarEvents"
    />
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";

// must manually include stylesheets for each plugin
import "@fullcalendar/core/main.css";
import "@fullcalendar/daygrid/main.css";
import "@fullcalendar/timegrid/main.css";

export default {
  name: 'schedule',
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data: function() {
    return {
      calendarPlugins: [
        // plugins must be defined in the JS
        dayGridPlugin,
        timeGridPlugin
      ],
      calendarWeekends: true,
      calendarEvents: [
        // initial event data
        { title: "Event Now", start: new Date() }
      ]
    };
  },
  methods: {
    
  }
};
</script>

<style>
.schedule {
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.calendar {
  margin: 0 auto;
  max-width: 900px;
}
</style>