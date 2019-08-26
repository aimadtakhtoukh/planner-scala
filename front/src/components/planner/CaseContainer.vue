<template>
  <div @click="onCaseClick()">
    <case v-show="!this.model.editable" :state="this.model.entry.dispo"></case>
    <dispo-picker v-show="this.model.editable" :model="this.model"></dispo-picker>
  </div>
</template>

<script>
  import Case from "./Case";
  import DispoPicker from "./DispoPicker";
  import moment from "moment";
  import PlannerModel from "../../services/PlannerModel";

  export default {
    name: "CaseContainer",
    components : {
      Case, DispoPicker
    },
    props : {
      //model : {type : Object, required: true, default: () => ({editable : false, entry : {dispo: "UNDEFINED", userId: 0, date: null}})},
      editable : {type : Boolean, required: true, default: false},
      userId : {type : Number, required: true, default: 0},
      day : {type : Object, required: true, default: moment()}
    },
    computed : {
      model : () => PlannerModel.getEntryForUser(this.userId, this.day)
    },
    methods : {
      onCaseClick() {
        if (this.editable) {
          this.model.editable = true;
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