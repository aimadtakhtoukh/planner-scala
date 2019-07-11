package controllers

import javax.inject.{Inject, Singleton}
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import security.{Security, SecurityUserRepo}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SecurityController @Inject()
(securityUserRepo: SecurityUserRepo, security : Security, cc : ControllerComponents)
(implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats with Logging {

  def getToken() = Action.async {
    implicit request =>
      Future {request.headers.get("Authorization").getOrElse("")}
        .flatMap(security.getDiscordUserFromToken)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

}
