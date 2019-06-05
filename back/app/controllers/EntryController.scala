package controllers

import javax.inject.{Inject, Singleton}
import model.{Availability, Entry}
import play.api.Logging
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.EntryRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EntryController @Inject()
  (entryRepo : EntryRepo, cc: ControllerComponents)
  (implicit ec : ExecutionContext) extends AbstractController(cc) with Logging {

  implicit val disponibilityFormats: Format[Availability.Value] = Json.formatEnum(Availability)
  implicit val entryFormats: OFormat[Entry] = Json.format[Entry]
  implicit def optionFormat[T : Format] : Format[Option[T]] = new Format[Option[T]] {
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) => implicitly[Writes[T]].writes(t)
      case None => Json.obj()
    }
  }

  def getById(id : Long) = Action.async {
    implicit request =>
      entryRepo.byId(id)
        .map(Json.toJson(_))
        .map(Ok(_))
  }

  def getByUser(userId : Long) = Action.async {
    implicit request =>
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


}
