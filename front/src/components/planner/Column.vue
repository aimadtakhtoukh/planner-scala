<template>
  <div class="column" v-bind:class="{'edited' : isColumnEditable()}">
    <div class="header">
      <span>{{user.name}}</span>
      <a v-if="isEditable()" v-on:click="editColumn()">
        <font-awesome-icon icon="edit"></font-awesome-icon>
      </a>
    </div>
    <case-container
      v-for="day in selectableDays"
      v-bind:model="getEntryFromDate(day.format('YYYY-MM-DD'))"
      v-bind:editable="isEditable()">
    </case-container>
  </div>
</template>

<script>
  import CurrentUser from "../../services/CurrentUser";
  import CaseContainer from "./CaseContainer";

  export default {
    name: "Column",
    components : {
      CaseContainer
    },
    props: {
      userWithEntries : {type : Object, required: true, default : {user : {id : 0, name : "..."}, entries : []}},
      selectableDays : {type : Array, required : true, default: []}
    },
    data() {
      return {
        user : this.userWithEntries.user,
        entries : this.userWithEntries.entries.map(entry => {return {entry : entry, editable : false}})
      }
    },
    methods: {
      getEntryFromDate(date) {
        let entry =  this.entries.filter(c => c.entry.date === date)[0];
        if (!entry) {
          // Si l'entrée n'est pas définie, on en crée une fausse, et on l'ajoute aux entrées existantes, pour
          // conserver son état jusqu'à l'envoi de la variable.
          entry = {
            entry : {
              dispo: "UNDEFINED",
              userId: this.user.id,
              date: date
            },
            editable: false
          };
          this.entries.push(entry);
        }
        return entry;
      },
      getEntriesForUser() {return this.entries.filter(e => e.entry.userId === this.user.id)},
      editColumn() {this.getEntriesForUser().forEach(e => e.editable = true)},
      isColumnEditable() {return this.getEntriesForUser().map(e => e.editable).some(e => e === true)},
      isEditable() {return CurrentUser.user.id === this.user.id}
    }
  }
</script>

<style scoped lang="scss">
  .column {
    border-right: 1px solid grey;
    min-width: 80px;
    flex: 1;

    &.edited {
      min-width: 160px;
    }

    .case {
      width: 100%;
      white-space: nowrap;

      span {
        width: 100%;
        text-align: center;
      }
    }
  }

  .header {
    border-bottom: 1px solid grey;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;
    white-space: nowrap;

    a {
      margin-left: 12px;
    }
  }
</style>