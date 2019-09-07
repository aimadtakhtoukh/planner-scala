<template>
    <div class="column">
        <div class="header" v-bind:class="{edited : isColumnEdited()}">
            <span>{{user.name}}</span>
            <a v-if="isConnectedUsers()" @click="editColumn()">
                <font-awesome-icon icon="edit"></font-awesome-icon>
            </a>
        </div>
        <case-container
                v-for="day in selectableDays"
                :date="day"
                :isConnectedUsers="isConnectedUsers()"
                :userId="userId"
                :key="day.format('YYYY-MM-DD')"
                ref="case">
        </case-container>
    </div>
</template>

<script>
    import store from "../../services/store/VuexStore";
    import CurrentUser from "../../services/CurrentUser";
    import CaseContainer from "./CaseContainer";

    export default {
        name: "Column",
        components : {
            CaseContainer
        },
        store,
        props: {
            selectableDays : {type : Array, required : false, default:() => []},
            userId : {type : Number, required: false, default: 0}
        },
        methods : {
            editColumn() {
                store.commit("updateColumnState", {
                    userId : this.userId,
                    mode : true
                });
            },
            isConnectedUsers() {return CurrentUser.state.user.id === this.user.id},
            isColumnEdited() {return store.getters.getColumnStateByUserId(this.userId)}
        },
        computed:  {
            user() {return store.getters.getUserWithEntriesById(this.userId).user}
        },
    }
</script>

<style scoped lang="scss">
    .column {
        border-right: 1px solid grey;
        flex: 1;

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
        justify-content: center;
        white-space: nowrap;
        text-align: center;
        display: inline-block;
        min-width: 79px;
        width: 100%;
        padding-left: 5px;
        padding-right: 5px;

        &.edited {
            min-width: 159px;
        }

        a {
            margin-left: 12px;
        }
    }
</style>