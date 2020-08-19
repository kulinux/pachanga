<template>
  <div class="day">
    <div class="day-header">
      <button @click="all">+</button>
      <span>{{name}}</span>
      <button @click="none">-</button>
    </div>
    <div class="day-hours">
      <Hour
        v-for="(hour, index) in hours"
        :hour="hour"
        :key="hour"
        :index="index"
        :selected="selected[index]"
        @hourClick="hourClick"
        @hourMouseEnter="hourMouseEnter"
        @hourMouseLeave="hourMouseLeave"
        :selectedOnMouseOver="selectedOnMouseOver"
      />
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
      default: () => [
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
      ],
    },
  },
  data() {
    return {
      selectedOnMouseOver: false,
      selected: this.hours.map(() => false),
      currentIndexSelectedEnter: -1,
      currentIndexSelectedLeave: -1,
      currentDirection: 0,
      from: { hour: -1, min: -1 },
      to: { hour: -1, to: -1 },
    };
  },
  methods: {
    hourClick: function (event) {
      if (!this.selectedOnMouseOver) {
        this.from = { hour: event.hour, min: event.min };
      } else {
        this.to = { hour: event.hour, min: event.min };
        this.$emit("rangeAdd", {
          day: this.name,
          from: {
            hour: this.from.hour,
            min: this.from.min,
          },
          to: {
            hour: this.to.hour,
            min: this.to.min
          }
        });
      }

      this.selectedOnMouseOver = !this.selectedOnMouseOver;
      this.$set(this.selected, event.index, true);
    },
    hourMouseEnter: function (event) {
      if (this.selectedOnMouseOver) {
        this.$set(this.selected, event.index, true);
      }
      this.currentIndexSelectedEnter = event.index;
    },
    hourMouseLeave: function (event) {
      this.currentIndexSelectedLeave = event.index;
    },
    all: function () {
      this.hours.forEach((value, index) =>
        this.$set(this.selected, index, true)
      );
      this.$emit("rangeAll", {
        day: this.name,
      });
    },
    none: function () {
      this.hours.forEach((value, index) =>
        this.$set(this.selected, index, false)
      );
      this.$emit("rangeClear", {
        day: this.name,
      });
    },
  },
};
</script>

<style>
.day-hours {
  display: flex;
  flex-direction: column;
  border-style: solid;
}

.day-header {
  margin: 0.5em;
  display: flex;
}
.day-header > * {
  flex-grow: 1;
}

.day-header > * + * {
  margin-left: 0.5em;
}

@media (max-width: 560px) {
  .day-header {
    flex-direction: column;
  }
}
</style>