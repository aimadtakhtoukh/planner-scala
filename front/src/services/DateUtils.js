import moment from "moment";

const DateUtils = {
  today: function() {return  moment().locale('fr')},
  nextWeekSunday: function() {return this.today().clone().add(7, 'days').endOf("isoWeek")},
  getRangeBetween: function(first, last) {
    let now = first.clone();
    let result = [];
    while (now.isSameOrBefore(last)) {
      result.push(now.clone());
      now.add(1, 'days')
    }
    return result;
  },
  getEditedDateRange: function() {return this.getRangeBetween(this.today(), this.nextWeekSunday())}
};

export default DateUtils