import UserService from "./UserService";

const CurrentUser = {
  user : {id : 3, name : "Aimad"},
  updateUser : function() {return Promise.resolve("Quelque chose")},//return UserService.getSelf().then(user => CurrentUser.user = user)},
  removeUser : function () {/*CurrentUser.user = {}*/}
};

//CurrentUser.updateUser();

export default CurrentUser