package controllers.actions

import javax.inject.Inject
import model.User
import play.api.Logging
import play.api.mvc._
import play.api.mvc.Results._
import repo.UserRepo
import security.{Security, SecurityUser, SecurityUserRepo}

import scala.concurrent.{Await, ExecutionContext, Future}

case class UserRequest[A](authorization : Option[String], user : Option[User], securityUser : Option[SecurityUser], request: Request[A]) extends WrappedRequest[A](request)

class UserAwareAction @Inject()(val parser: BodyParsers.Default, security : Security, userRepo : UserRepo, securityUserRepo: SecurityUserRepo)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] with ActionTransformer[Request, UserRequest] with Logging {

  def transform[A](request: Request[A]): Future[UserRequest[A]] =
    request.headers.get("Authorization") match {
      case Some(authorization) =>
        for {
          discordUserOption <- security.getDiscordUserFromToken(authorization)
          securityUserOption <- discordUserOption.map(_.id).map(securityUserRepo.bySecurityId).getOrElse(Future.successful(None))
          userOption <- securityUserOption.map(_.userId).map(userRepo.byId).getOrElse(Future.successful(None))
        } yield {logger.error(authorization); logger.error(securityUserOption.toString); logger.error(userOption.toString); UserRequest(Some(authorization), userOption, securityUserOption, request)}
      case None => Future.successful(UserRequest(None, None, None, request))
    }
}

class AnyTokenAllowed(implicit ec : ExecutionContext) extends ActionFilter[UserRequest] {
  override protected def filter[A](request: UserRequest[A]): Future[Option[Result]] = Future.successful {
    if (request.authorization.isEmpty) {
      Some(Forbidden)
    } else {
      None
    }
  }

  override protected def executionContext: ExecutionContext = ec
}

class AnyUserAllowed(implicit ec : ExecutionContext) extends ActionFilter[UserRequest] {
  override protected def filter[A](request: UserRequest[A]): Future[Option[Result]] = Future.successful {
    if (request.user.isEmpty) {
      Some(Forbidden)
    } else {
      None
    }
  }

  override protected def executionContext: ExecutionContext = ec
}