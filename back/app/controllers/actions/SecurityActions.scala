package controllers.actions

import javax.inject.Inject
import model.User
import play.api.mvc._
import repo.UserRepo
import security.{Security, SecurityUser, SecurityUserRepo}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

case class UserRequest[A](user : Option[User], securityUser : Option[SecurityUser], request: Request[A]) extends WrappedRequest[A](request)

class UserAwareAction @Inject()(val parser: BodyParsers.Default, securityUserRepo: SecurityUserRepo, security : Security, userRepo : UserRepo)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] with ActionTransformer[Request, UserRequest] {

  def transform[A](request: Request[A]) =
    Future.successful {
      request.headers.get("Authorization")
        .map {
          authorization =>
            Await.result(
              for {
                discordUserOption <- security.getDiscordUserFromToken(authorization)
                discordUser <- discordUserOption
                securityUserOption <- securityUserRepo.bySecurityId(discordUser.id)
                securityUser <- securityUserOption
                userOption <- userRepo.byId(securityUser.userId)
              } yield UserRequest(userOption, securityUserOption, request),
              Duration.Inf
            )
        }
        .getOrElse(UserRequest(None, None, request))
    }
}