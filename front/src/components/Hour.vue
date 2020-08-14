<template>
  <div class="hour" 
    v-bind:class="{'is-selected': selected}"
    @click="click"
    @mouseover="mouseOver">
    {{hour}}:{{min}}
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
    index: Number
  },
  methods: {
    getState: function() {
      return {'hour': this.hour, 'min': this.min, 'index': this.index}
    },
    click: function() {
      this.$emit('hourClick', this.getState());
    },
    mouseOver: function() {
      this.$emit('hourMouseOver', this.getState());
    }
  }

};
</script>

<style scoped>
.hour {
  border-style: solid;
  border-width: 0.02em;
}

.hour:hover {
  background-color: gray;
  color: white;
  cursor: pointer;
}
.is-selected {
  background-color: black;
  color: white;
}

</style>