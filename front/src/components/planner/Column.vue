<template>
    <div>
        <div class="column">
            <div class="header">
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
    </div>
</template>

<script>
    import store from "../../services/VuexStore";
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
            editColumn() {this.$refs.case.forEach(c => c.onCaseClick())},
            isConnectedUsers() {return CurrentUser.user.id === this.user.id}
        },
        computed:  {
            user() {
                return store.getters.getUserWithEntriesById(this.userId).user
            }
        },
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