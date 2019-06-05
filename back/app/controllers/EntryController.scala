package controllers

import javax.inject.{Inject, Singleton}
import model.Entry
import play.api.Logging
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.EntryRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EntryController @Inject()
  (entryRepo : EntryRepo, cc: ControllerComponents)
  (implicit ec : ExecutionContext) extends AbstractController(cc) with Logging with StandardFormats {

  def getById(id : Long) = Action.async {
      entryRepo.byId(id)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getByUser(userId : Long) = Action.async {
      entryRepo.byUser(userId)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def add() = Action.async(parse.json) {
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
          entryRepo.add(entry)
            .map(_ => Ok(""))
            .recover {case ex => BadRequest(ex.getMessage)  }
      )
  }

  def withUser() = Action.async {
      entryRepo.usersWithEntries()
        .map(Json.toJson(_))
        .map(Ok(_))
  }


}
