<template>
  <div class="selector">
    <button class="btn btn-xs on" v-on:click="onButtonClick('ON')">
      <font-awesome-icon icon="check"></font-awesome-icon>
    </button>
    <button class="btn btn-xs off" v-on:click="onButtonClick('OFF')">
      <font-awesome-icon icon="ban"></font-awesome-icon>
    </button>
    <button class="btn btn-xs maybe" v-on:click="onButtonClick('MAYBE')">
      <span>(</span>
      <font-awesome-icon icon="check"></font-awesome-icon>
      <span>)</span>
    </button>
    <button class="btn btn-xs planning" v-on:click="onButtonClick('PLANNING')">
      <span>!</span>
    </button>
  </div>
</template>

<script>
  import moment from "moment";
  import EntryService from "../../services/EntryService";
  import CurrentUser from "../../services/CurrentUser";

  export default {
    name: "DispoPicker",
    props : {
      model : {type : Object, required : true, default : {editable : false, entry : {dispo: "UNDEFINED", userId: 0, date: null}}}
    },
    methods : {
      onButtonClick(dispo) {
        EntryService.addEntry({
          userId : CurrentUser.user.id,
          dispo : dispo,
          date: this.model.entry.date
        })
      }
    }
  }
</script>

<style scoped lang="scss">
  .selector {
    display: flex;

    .button {
      flex : 1;
      width: 24px;
      max-height: 24px;
      white-space: nowrap;
      text-align: center;
      padding: 0;
      border-width: 0;
    }

    .on, .on * {
      @extend .button;
      background-color : lightgreen;
      color : green;
    }

    .off, .off * {
      @extend .button;
      background-color : lightcoral;
      color : darkred;
    }

    .maybe, .maybe * {
      @extend .button;
      background-color : #fbc97f;
      color : darkorange;
    }

    .planning, .planning * {
      @extend .button;
      background-color: lightblue;
      color : darkblue;
      font-weight: bolder;
    }
  }
</style>