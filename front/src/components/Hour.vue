<template>
  <div class="hour" 
    v-bind:class="{'is-selected': selected, 'selected-on-hover': selectedOnMouseOver}"
    @click="click"
    @mouseenter="mouseEnter"
    @mouseleave="mouseLeave">
    {{hour}}:{{this.formatMinutes()}}
  </div>
</template>

<script>
export default {
  name: "Hour",
  props: {
    hour: Number,
    min: {
        type: Number,
        default: 0
    },
    selected: Boolean,
    selectedOnMouseOver: Boolean,
    index: Number
  },
  methods: {
    getState: function() {
      return {'hour': this.hour, 'min': this.min, 'index': this.index}
    },
    click: function() {
      this.$emit('hourClick', this.getState());
    },
    mouseEnter: function() {
      if(this.selectedOnMouseOver) this.$emit('hourMouseEnter', this.getState());
    },
    mouseLeave: function() {
      if(this.selectedOnMouseOver) this.$emit('hourMouseLeave', this.getState());
    },
    formatMinutes() {
      if((this.min + '').length == 1) return ('0' + this.min);
      return this.min;
    }
  }

};
</script>

<style scoped>
.hour {
  border-style: solid;
  border-width: 0.02em;
  cursor: pointer;
}

.hour:hover {
  background-color: gray;
  color: white;
}
.is-selected {
  background-color: black;
  color: white;
}
.selected-on-hover {
  cursor: grabbing;
}

</style>