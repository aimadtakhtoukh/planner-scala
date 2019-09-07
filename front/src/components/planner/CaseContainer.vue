<template>
    <div @click="onCaseClick()">
        <case v-show="!this.editing" :state="this.entry.dispo"></case>
        <dispo-picker
                :userId="userId"
                :date="date"
                v-show="this.editing"
        ></dispo-picker>
    </div>
</template>

<script>
    import moment from "moment";
    import store from "../../services/store/VuexStore";
    import Case from "./Case";
    import DispoPicker from "./DispoPicker";
    import CurrentUser from "../../services/CurrentUser";

    export default {
        name: "CaseContainer",
        components : {
            Case, DispoPicker
        },
        store,
        props : {
            isConnectedUsers : {type : Boolean, required: true, default: false},
            userId : {type : Number, required: true, default: 0},
            date : {type : Object, required: true, default: moment()}
        },
        computed : {
            entry : function () {return store.getters.getEntryByUserIdAndDate(this.userId, this.date.format("YYYY-MM-DD"))},
            editing : function() {return store.getters.getCaseStateByUserIdAndDate(this.userId, this.date.format("YYYY-MM-DD"))}
        },
        methods : {
            onCaseClick() {
                if (CurrentUser.state.user.id === this.userId) {
                    store.commit("updateCaseState", {
                        userId : this.userId,
                        date: this.date.format("YYYY-MM-DD"),
                        mode : true
                    });
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