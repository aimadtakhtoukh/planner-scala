package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import schema.{User, UserRepo}

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()
    (userRepo : UserRepo, cc: ControllerComponents)
    (implicit ec : ExecutionContext) extends AbstractController(cc) {

  implicit val userFormats: OFormat[User] = Json.format[User]

  def getById(id : Long): Action[AnyContent] = Action.async {
    implicit request =>
      userRepo.getById(id)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

}
