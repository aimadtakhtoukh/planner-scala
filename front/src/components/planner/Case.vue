<template>
  <div class="case">
    <div v-if="state === 'ON'" class="on">
      <font-awesome-icon icon="check"></font-awesome-icon>
    </div>
    <div v-if="state === 'OFF'" class="off">
      <font-awesome-icon icon="ban"></font-awesome-icon>
    </div>
    <div v-if="state === 'MAYBE'" class="maybe">
      <span>(</span>
      <font-awesome-icon icon="check"></font-awesome-icon>
      <span>)</span>
    </div>
    <div v-if="state === 'PLANNING'" class="planning">
      <span>!</span>
    </div>
    <div v-if="!isAValidState(state)" class="default">
      <span>?</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Case",
    props: {
      state : {type : String, required : true, default : "UNDEFINED"}
    },
    methods: {
      isAValidState(state) {
        return ["ON", "OFF", "MAYBE", "PLANNING"].indexOf(state) !== -1
      }
    }
  }
</script>

<style scoped lang="scss">
  .case {
    text-align: center;
  }

  .on, .on * {
    background-color : lightgreen;
    color : green;
  }

  .off, .off * {
    background-color : lightcoral;
    color : darkred;
  }

  .maybe, .maybe * {
    background-color : #fbc97f;
    color : darkorange;
  }

  .planning, .planning * {
    background-color: lightblue;
    color : darkblue;
    font-weight: bolder;
  }

  .default, .default * {
    background-color : #36393f;
    color : grey;

    span {
      font-weight: bold;
    }
  }
</style>