package controllers

import controllers.actions.{AnyTokenAllowed, AnyUserAllowed, UserAwareAction}
import javax.inject._
import model.User
import play.api.libs.json._
import play.api.mvc._
import repo.UserRepo
import security.{Security, SecurityUser, SecurityUserRepo}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()
    (userRepo : UserRepo, security: Security, securityUserRepo: SecurityUserRepo, userAwareAction: UserAwareAction, cc: ControllerComponents)
    (implicit ec : ExecutionContext) extends AbstractController(cc) with StandardFormats {

  def getById(id : Long) = userAwareAction.andThen(new AnyTokenAllowed).async {
      userRepo.byId(id)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getByName(name : String) = userAwareAction.andThen(new AnyTokenAllowed).async {
    userRepo.byName(name).map(Json.toJson(_)).map(Ok(_))
  }

  def all() = userAwareAction.andThen(new AnyTokenAllowed).async {
    userRepo.all().map(Json.toJson(_)).map(Ok(_))
  }

  def add() = userAwareAction.andThen(new AnyTokenAllowed).async(parse.json) {
    implicit request =>
      if (request.user.isDefined) {
        Future {Forbidden("The user already exists.")}
      }
      request.body.validate[User].fold(
        errors =>
          Future {BadRequest(errors.mkString)},
        user => {
          Future {request.headers.get("Authorization").getOrElse("")}
            .flatMap(security.getDiscordUserFromToken)
            .flatMap {
              case None =>
                Future {BadRequest("Incorrect token")}
              case Some(discordUser) =>
                userRepo.add(user)
                  .map(id => securityUserRepo.add(SecurityUser(userId = id, securityId = discordUser.id, `type` = "DISCORD")))
                  .map(_ => Ok(""))
                  .recover { case ex => BadRequest(ex.getMessage) }
            }

        }
      )
  }

  def update() = userAwareAction.andThen(new AnyUserAllowed).async(parse.json) {
    implicit request =>
      request.body.validate[User].fold(
        errors =>
          Future {BadRequest(errors.mkString)},
        user =>
          Future {
            request.user
              .flatMap(_.id)
              .map(id => userRepo.update(id, user.name))
              .map(_ => Ok(""))
              .getOrElse(BadRequest(""))
          }
      )
  }
}
