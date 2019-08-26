<template>
    <div class="selector">
        <button class="btn btn-xs on" @click="onButtonClick('ON')">
            <font-awesome-icon icon="check"></font-awesome-icon>
        </button>
        <button class="btn btn-xs off" @click="onButtonClick('OFF')">
            <font-awesome-icon icon="ban"></font-awesome-icon>
        </button>
        <button class="btn btn-xs maybe" @click="onButtonClick('MAYBE')">
            <span>(</span>
            <font-awesome-icon icon="check"></font-awesome-icon>
            <span>)</span>
        </button>
        <button class="btn btn-xs planning" @click="onButtonClick('PLANNING')">
            <span>!</span>
        </button>
    </div>
</template>

<script>
    import EntryService from "../../services/EntryService";
    import store from "../../services/VuexStore";

    export default {
        name: "DispoPicker",
        props : {
            userId : {type : Number, required: true, default: 0},
            date : {type : Object, required: true, default: null}
        },
        methods : {
            onButtonClick(dispo) {
                EntryService.addEntry({
                    userId : this.userId,
                    dispo : dispo,
                    date: this.date.format("YYYY-MM-DD")
                })
                .then(() => store.dispatch("update"))
                .then(() => this.$emit("entrysent"))
            }
        }
    }
</script>

<style scoped lang="scss">
    .selector {
        display: flex;

        .button {
            flex : 1;
            width: 40px;
            height: 24px;
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