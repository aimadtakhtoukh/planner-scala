<template>
  <section class="calendar">
    <div class="left-column">
      <div class="left-header">
        <span>Jour</span>
      </div>
      <div class="left-body">
        <div class="case" v-for="day in selectableDays">
          <span>{{day.format("dddd DD")}}</span>
        </div>
      </div>
    </div>

    <div class="case-container">
      <column
        class="column"
        v-for="userWithEntries in usersWithEntries"
        :userWithEntries="userWithEntries"
        :selectableDays="selectableDays">
      </column>
    </div>
  </section>
</template>

<script>
  import DateUtils from "../../services/DateUtils";
  import UserService from "../../services/UserService";
  import EntryService from "../../services/EntryService";
  import Column from "./Column";
  import CurrentUser from "../../services/CurrentUser";

  export default {
    name: "Planner",
    components : {
      Column
    },
    data() {
      return {
        selectableDays : [],
        usersWithEntries : []
      }
    },
    methods: {
      init() {
        this.selectableDays = DateUtils.getEditedDateRange();
        const user = CurrentUser.user;
        EntryService.getUsersAndEntries().then(
          (usersWithEntries) => {
            const id = user.id;
            this.usersWithEntries = usersWithEntries.sort(
              (user1, user2) => {
                if (user1.user.id === id) {return -1}
                if (user2.user.id === id) {return 1}
                return 0
              }
            )
          }
        )
      }
    },
    mounted() {
      this.init()
    }
  }
</script>

<style scoped lang="scss">
  .calendar {
    margin: 0 5%;
    border: 1px solid white;
    display: flex;
    flex-direction: row;
  }

  .header {
    border-bottom: 1px solid grey;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;

    a {
      margin-left: 12px;
    }
  }

  .left-column  {
    display: flex;
    flex-direction: column;
    border-right: 1px solid grey;
    width: 120px;
    height: 100%;

    .left-header {
      flex: 1;
      @extend .header;
    }

    .left-body {
      height: 100%;
      flex: 1;
      display: flex;
      flex-direction: column;

      .case {
        flex : 1;
        width: 100%;
        text-align: center;
        padding-left: 10px;
        padding-right: 10px;
      }
    }
  }

  .column {
    flex : 1;
  }

  .case-container {
    overflow-x: auto;
    flex: 1;
    flex-direction: row;
    display: flex;
  }
</style>