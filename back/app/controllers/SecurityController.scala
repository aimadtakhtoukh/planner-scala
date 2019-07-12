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
(securityUserRepo: SecurityUserRepo, security : Security, userRepo : UserRepo, cc : ControllerComponents)
(implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats with Logging {

  def getToken(): Action[AnyContent] = Action.async {
    implicit request =>
      Future {request.headers.get("Authorization").getOrElse("")}
        .flatMap(security.getDiscordUserFromToken)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getUser() = new UserAwareAction() {
    implicit request =>
      Ok(request.user.toString)
  }

  /*

  def getUser() = Action.async {
    implicit request =>
      Future {request.headers.get("Authorization").getOrElse("")}
        .flatMap(security.getDiscordUserFromToken)
        .flatMap(_.map(_.id)
            .map(securityUserRepo.bySecurityId)
            .getOrElse(Future.failed(new IllegalStateException("The user doesn't exist")))
        )
        .flatMap(_.map(_.userId)
          .map(userRepo.byId)
          .getOrElse(Future.failed(new IllegalStateException("The user doesn't exist"))))
        .map(Json.toJson(_))
        .map(Ok(_))
        .recover {
          case e => BadRequest(e.getLocalizedMessage)
        }
  }
  */

}
