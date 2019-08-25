const PlannerModel = {
  usersWithEntries : [],
  saveNewModel(usersWithEntries) {
    this.usersWithEntries = usersWithEntries
  },
  getUserWithEntriesById(id) {
    return this.usersWithEntries.filter(user => user.id === id)[0] || {}
  },
  getEntryForUser(id, date) {
    return this.getUserWithEntriesById(id).entries.filter(entry => entry.date === date)[0] || {dispo: "UNDEFINED", userId: id, date: date}
  }
};

export default PlannerModel