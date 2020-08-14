<template>
  <div class="day">
    <p>{{name}}</p>
    <div class="day-hours">
      <Hour v-for="(hour, index) in hours"
        :hour="hour"
        :key="hour"
        :index="index"
        :selected="selected[index]"
        @hourClick="hourClick"
        @hourMouseOver="hourMouseOver"
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
      selected: this.hours.map(() => false)
    }
  },
  methods: {
    hourClick: function(event) {
      this.selectedOnMouseOver = !this.selectedOnMouseOver;
      this.$set(this.selected, event.index, true);
      console.log('HourClick', event)
    },
    hourMouseOver: function(event) {
      if(this.selectedOnMouseOver) {
        console.log('HourMouseOver', event)
        this.$set(this.selected, event.index, true);
      }
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