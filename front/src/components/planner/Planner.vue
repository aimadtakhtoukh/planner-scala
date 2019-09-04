<template>
    <section class="calendar">
        <div class="left-column">
            <div class="left-header">
                <span>Jour</span>
            </div>
            <div class="left-body">
                <div class="case" v-for="day in selectableDays" :key="day.format('ddddDD')">
                    <span>{{day.format("dddd DD")}}</span>
                </div>
            </div>
        </div>
        <div class="case-container">
            <column
                    v-for="userWithEntries in usersWithEntries"
                    :selectableDays="selectableDays"
                    :userId="userWithEntries.user.id"
                    :key="userWithEntries.user.id">
            </column>
        </div>
    </section>
</template>

<script>
    import DateUtils from "../../services/DateUtils";
    import store from "../../services/store/VuexStore";
    import Column from "./Column";

    export default {
        name: "Planner",
        store,
        components : {
            Column
        },
        computed : {
            selectableDays : () => DateUtils.getEditedDateRange(),
            usersWithEntries : () => store.state.usersWithEntries
        },
        mounted() {
            this.$store.dispatch("update")
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

    .case-container {
        overflow-x: auto;
        flex: 1;
        flex-direction: row;
        display: flex;
    }
</style>