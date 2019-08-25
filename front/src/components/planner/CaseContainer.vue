<template>
  <case v-show="!model.editable" v-bind:state="model.entry.dispo" v-on:click="onCaseClick()"></case>
  <dispo-picker v-show="model.editable" v-bind:model="model"></dispo-picker>
</template>

<script>
  import Case from "./Case";
  import DispoPicker from "./DispoPicker";

  export default {
    name: "CaseContainer",
    components : {
      Case, DispoPicker
    },
    props : {
      model : {type : Object, required: true, default : {editable : false, entry : {dispo: "UNDEFINED", userId: 0, date: null}}},
      editable : {type : Boolean, required: true, default: false}
    },
    methods : {
      onCaseClick() {
        console.log("click")
        if (this.editable) {
          this.model.deployed = true;
        }
      }
    }
  }
</script>

<style scoped>
  div {
    height: 24px;
  }
</style>