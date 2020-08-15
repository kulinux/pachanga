<template>
  <div class="day">
    <p>
      <button @click="all">+</button>
      {{name}}
      <button @click="none">-</button>
    </p>
    <div class="day-hours">
      <Hour v-for="(hour, index) in hours"
        :hour="hour"
        :key="hour"
        :index="index"
        :selected="selected[index]"
        @hourClick="hourClick"
        @hourMouseEnter="hourMouseEnter"
        @hourMouseLeave="hourMouseLeave"
        :selectedOnMouseOver="selectedOnMouseOver" />
    </div>
  </div>
</template>

<script>
import Hour from "./Hour.vue";
export default {
  name: "Day",
  components: {
    Hour,
  },
  props: {
    name: {
      type: String,
      required: true,
    },
    hours: {
        type: Array, 
        default: () => [6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
    }
  },
  data() {
    return {
      selectedOnMouseOver: false,
      selected: this.hours.map(() => false),
      currentIndexSelectedEnter: -1,
      currentIndexSelectedLeave: -1,
      currentDirection: 0
    }
  },
  methods: {
    hourClick: function(event) {
      console.log('HourClick', event)
      this.selectedOnMouseOver = !this.selectedOnMouseOver;
      this.$set(this.selected, event.index, true);
    },
    hourMouseEnter: function(event) {
      if(this.selectedOnMouseOver) {
        this.$set(this.selected, event.index, true);
      }
      this.currentIndexSelectedEnter = event.index;
    },
    hourMouseLeave: function(event) {
      this.currentIndexSelectedLeave = event.index;
    },
    all: function() {
      this.hours.forEach((value, index) => 
        this.$set(this.selected, index, true)
      )
    },
    none: function() {
      this.hours.forEach((value, index) => 
        this.$set(this.selected, index, false)
      )
    }

  }
};
</script>

<style>
.day-hours {
  display: flex;
  flex-direction: column;
  border-style: solid;
}
</style>