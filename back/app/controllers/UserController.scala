package controllers

import javax.inject._
import model.User
import play.api.libs.json._
import play.api.mvc._
import repo.UserRepo
import security.{Security, SecurityUser, SecurityUserRepo}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()
    (userRepo : UserRepo, security: Security, securityUserRepo: SecurityUserRepo, cc: ControllerComponents)
    (implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats {

  def getById(id : Long) = Action.async {
      userRepo.byId(id)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getByName(name : String) = Action.async {
    userRepo.byName(name).map(Json.toJson(_)).map(Ok(_))
  }

  def all() = Action.async {
    userRepo.all().map(Json.toJson(_)).map(Ok(_))
  }

  def add() = Action.async(parse.json) {
    implicit request =>
      request.body.validate[User].fold(
        errors =>
          Future {BadRequest(errors.mkString)},
        user => {
          Future {request.headers.get("Authorization").getOrElse("")}
            .flatMap(security.getDiscordUserFromToken)
            .flatMap {
              case Some(discordUser) =>
                userRepo.add(user)
                  .map(id => securityUserRepo.add(SecurityUser(userId = id, securityId = discordUser.id, origin = "DISCORD")))
                  .map(_ => Ok(""))
                  .recover { case ex => BadRequest(ex.getMessage) }
              case None =>
                Future {BadRequest("Incorrect token")}
            }

        }
      )
  }

  def update() = Action.async(parse.json) {
    implicit request =>
      request.body.validate[User].fold(
        errors =>
          Future {BadRequest(errors.mkString)},
        user =>
          Future {
            user.id
              .map(id => userRepo.update(id, user.name))
              .map(_ => Ok(""))
              .getOrElse(BadRequest(""))
          }
      )
  }
}
