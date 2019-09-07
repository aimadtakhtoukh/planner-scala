package controllers

import controllers.actions.{AnyTokenAllowed, AnyUserAllowed, UserAwareAction}
import controllers.traits.{LocalDateConversion, StandardFormats}
import javax.inject.{Inject, Singleton}
import model.Entry
import play.api.Logging
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.EntryRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EntryController @Inject()
(entryRepo : EntryRepo, userAwareAction: UserAwareAction, cc: ControllerComponents)
(implicit ec : ExecutionContext) extends AbstractController(cc) with Logging with StandardFormats with LocalDateConversion {

  def getById(id : Long) = userAwareAction.andThen(new AnyTokenAllowed).async {
    entryRepo.byId(id)
      .map(Json.toJson(_))
      .map(Ok(_))
  }

  def getByUser(userId : Long) = userAwareAction.andThen(new AnyTokenAllowed).async {
    entryRepo.byUser(userId)
      .map(Json.toJson(_))
      .map(Ok(_))
  }

  def add() = userAwareAction.andThen(new AnyUserAllowed).async(parse.json) {
    implicit request =>
      request.body.validate[Entry].fold(
        errors =>
          Future {
            errors.foreach {
              case (jsPath, jsError) =>
                logger.info(jsPath.toString())
                jsError.map(_.toString).foreach(logger.info(_))
            }
            BadRequest(errors.mkString)
          },
        entry =>
          if (request.user.exists(_.id.exists(_ == entry.userId))) {
            entryRepo.add(entry)
              .map(_ => Ok(""))
              .recover { case ex => BadRequest(ex.getMessage) }
          } else {
            Future.successful(Forbidden("Wrong user"))
          }
      )
  }

  def withUser(from : String, to : String) = userAwareAction.andThen(new AnyTokenAllowed).async {
    entryRepo.usersWithEntries(from.toDate(), to.toDate())
      .map(Json.toJson(_))
      .map(Ok(_))
  }


}
