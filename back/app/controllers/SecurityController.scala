package controllers

import controllers.actions.UserAwareAction
import controllers.traits.StandardFormats
import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repo.UserRepo
import security.{Security, SecurityUserRepo}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@Singleton
class SecurityController @Inject()
  (securityUserRepo: SecurityUserRepo, security : Security, userRepo : UserRepo, userAwareAction: UserAwareAction, cc : ControllerComponents)
  (implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats with Logging {

  def getToken(): Action[AnyContent] = Action.async {
    implicit request =>
      Future {request.headers.get("Authorization").getOrElse("")}
        .flatMap(security.getDiscordUserFromToken)
        .map(
          _
            .map(Json.toJson(_))
            .map(Ok(_))
            .getOrElse(Unauthorized("{\"error\" : \"Unknown token\"}"))
        )
  }

  def getUser: Action[AnyContent] = userAwareAction {
    implicit request =>
      request.user match {
        case Some(user) =>
          Ok(Json.toJson(user))
        case None =>
          Await.result(
            request.authorization
              .map(security.getDiscordUserFromToken)
              .getOrElse(Future.failed(new IllegalStateException(""))),
              Duration.Inf
          ) match {
            case Some(_) => Unauthorized("{\"error\" : \"Unknown user\"}")
            case None => Unauthorized("{\"error\" : \"Unknown token\"}")
          }
      }
  }

}
