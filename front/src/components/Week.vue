<template>
  <div>
    <p>Soy week</p>
    <div class="week">
      <Day name="Monday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Tuesday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Wednesday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Thursday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Friday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Saturday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
      <Day name="Sunday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear" />
    </div>
  </div>
</template>

<script>
import Day from "./Day.vue";

export default {
  name: "Week",
  components: {
    Day,
  },
  data() {
    return {
      schedule: {},
    };
  },
  methods: {
    rangeAdd: function (ev) {
      console.log("RangeAdd", ev);
      let newItem = { from: ev.from, to: ev.to };
      if (!this.schedule[ev.day]) {
        this.schedule[ev.day] = [newItem];
      } else {
        this.schedule[ev.day] = this.schedule[ev.day].concat(newItem);
      }
    },
    rangeClear: function (ev) {
      delete this.schedule[ev.day];
      console.log("RangeClear", ev);
    },
    rangeAll: function (ev) {
      this.schedule[ev.day] = [
        { from: { hour: 0, min: 0 }, to: { hour: 23, min: 59 } },
      ];
      console.log("RangeAll", ev);
    },
  },
};
</script>


<style>
.week {
  display: flex;
  font-size: 12px;
}

.day {
  flex-grow: 1;
}
</style>