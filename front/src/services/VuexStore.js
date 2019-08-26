import Vue from 'vue';
import Vuex from 'vuex';
import EntryService from "./EntryService";
import CurrentUser from "./CurrentUser";

Vue.use(Vuex);

const store = new Vuex.Store({
    state : {
        usersWithEntries : [],
        usersWithEntriesLookup : {},
        entryLookup : {}
    },
    mutations: {
        update(state, usersWithEntries) {
            state.usersWithEntries = usersWithEntries;
            state.usersWithEntriesLookup = usersWithEntries.reduce((accumulator, userWithEntries) => {
                accumulator[userWithEntries.user.id] = userWithEntries;
                return accumulator;
            }, {});
            state.entryLookup = usersWithEntries.reduce((accumulator, userWithEntries) => {
                accumulator[userWithEntries.user.id] = userWithEntries.entries.reduce((accumulator, entry) => {
                    accumulator[entry.date] = entry;
                    return accumulator;
                }, {});
                return accumulator;
            }, {});
        }
    },
    actions: {
        update({commit}) {
            return EntryService.getUsersAndEntries().then(
                (usersWithEntriesRaw) => {
                    const id = (CurrentUser.state.user || {id: 0}).id;
                    const usersWithEntries = usersWithEntriesRaw
                        .filter(userWithEntry => !!userWithEntry.entries)
                        .sort(
                            (user1, user2) => {
                                if (user1.user.id === id) {
                                    return -1
                                }
                                if (user2.user.id === id) {
                                    return 1
                                }
                                return 0
                            }
                        )
                    commit("update", usersWithEntries)
                }
            )
        }
    },
    getters: {
        getUserWithEntriesById : (state) => (id) =>
            state.usersWithEntriesLookup[id] ||
            {user : {id : 0, name : "..."}, entries : []},
        getEntryByUserIdAndDate : (state) => (id, date) => state.entryLookup[id][date] ||
            {dispo: "UNDEFINED", userId: id, date: date}

    }
});

export default store