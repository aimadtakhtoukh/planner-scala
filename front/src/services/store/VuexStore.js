import Vue from 'vue';
import Vuex from 'vuex';
import EntryService from "../EntryService";
import CurrentUser from "../CurrentUser";
import DateUtils from "../DateUtils";

Vue.use(Vuex);

const store = new Vuex.Store({
  state : {
    usersWithEntries : [],
    usersWithEntriesLookup : {},
    entryLookup : {},
    columnsState : {}
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
      state.columnsState = usersWithEntries.reduce((accumulator, userWithEntries) => {
        accumulator[userWithEntries.user.id] = DateUtils.getEditedDateRange()
          .map(d => d.format("YYYY-MM-DD"))
          .reduce((accumulator, date) => {
            state.columnsState[userWithEntries.user.id] = state.columnsState[userWithEntries.user.id] || {};
            accumulator[date] = state.columnsState[userWithEntries.user.id][date] || false;
            return accumulator;
          }, {});
        return accumulator;
      }, {});
    },
    updateCaseState(state, payload) {
      state.columnsState = state.columnsState || {};
      state.columnsState[payload.userId] = state.columnsState[payload.userId] || {};
      state.columnsState[payload.userId][payload.date] = payload.mode
    },
    updateColumnState(state, payload) {
      state.columnsState = state.columnsState || {};
      Object.keys(state.columnsState[payload.userId]).forEach(key => state.columnsState[payload.userId][key] = payload.mode)
    }
  },
  actions: {
    update({commit}) {
      return EntryService.getUsersAndEntries().then(
        (usersWithEntriesRaw) => {
          const id = (CurrentUser.state.user || {id: 0}).id;
          const currentUserWithEntry = usersWithEntriesRaw.find(u => u.user.id === id);
          const usersWithEntries = usersWithEntriesRaw
            .filter(userWithEntry => userWithEntry.user.id !== id)
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
          commit("update",  [currentUserWithEntry].concat(usersWithEntries))
        }
      )
    }
  },
  getters: {
    getUserWithEntriesById : (state) => (id) =>
      state.usersWithEntriesLookup[id] ||
      {user : {id : 0, name : "..."}, entries : []},
    getEntryByUserIdAndDate : (state) => (id, date) => state.entryLookup[id][date] ||
      {dispo: "UNDEFINED", userId: id, date: date},
    getCaseStateByUserIdAndDate : (state) => (id, date) => state.columnsState[id][date] || false,
    getColumnStateByUserId : (state) => (id) =>
      Object.keys(state.columnsState[id]).map(key => state.columnsState[id][key]).some(e => e === true)
  }
});

export default store