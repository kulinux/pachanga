<template>
    <div>
        <p>Soy week</p>
    <div class="week">
        <Day name="Monday"/>
        <Day name="Tuesday"/>
        <Day name="Wednesday"/>
        <Day name="Thursday"/>
        <Day name="Friday"/>
        <Day name="Saturday"/>
        <Day name="Sunday"/>
    </div>
    </div>
</template>

<script>

import Day from './Day.vue';

export default {
    name: 'Week',
    components: {
        Day
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