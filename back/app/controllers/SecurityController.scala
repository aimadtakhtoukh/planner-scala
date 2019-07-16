package controllers

import controllers.actions.UserAwareAction
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repo.UserRepo
import security.{Security, SecurityUserRepo}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SecurityController @Inject()
  (securityUserRepo: SecurityUserRepo, security : Security, userRepo : UserRepo, userAwareAction: UserAwareAction, cc : ControllerComponents)
  (implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats with Logging {

  def getToken(): Action[AnyContent] = Action.async {
    implicit request =>
      Future {request.headers.get("Authorization").getOrElse("")}
        .flatMap(security.getDiscordUserFromToken)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getUser() = userAwareAction {
    implicit request =>
      Ok(Json.toJson(request.user))
  }
}
