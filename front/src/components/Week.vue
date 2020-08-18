<template>
  <div>
    <p>Soy week</p>
    <div class="week">
      <Day name="Monday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Tuesday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Wednesday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Thursday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Friday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Saturday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
      <Day name="Sunday" @rangeAdd="rangeAdd" @rangeAll="rangeAll" @rangeClear="rangeClear"/>
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
  methods: {
      rangeAdd: function(ev) {
          console.log('RangeAdd', ev);
      },
      rangeClear: function(ev) {
          console.log('RangeClear', ev);
      },
      rangeAll: function(ev) {
          console.log('RangeAll', ev);
      }
  }
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