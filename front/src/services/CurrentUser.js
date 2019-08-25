import UserService from "./UserService";

const CurrentUser = {
  user : {},
  updateUser : function() {return UserService.getSelf().then(user => CurrentUser.user = user)},
  removeUser : function () {CurrentUser.user = {}}
};

CurrentUser.updateUser();

export default CurrentUser