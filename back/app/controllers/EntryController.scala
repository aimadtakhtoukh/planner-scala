package controllers

import javax.inject.{Inject, Singleton}
import model.Models.Entry
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.EntryRepo

import scala.concurrent.ExecutionContext

@Singleton
class EntryController @Inject()
(entryRepo : EntryRepo, cc: ControllerComponents)
(implicit ec : ExecutionContext) extends AbstractController(cc){

  implicit val entryFormats: OFormat[Entry] = Json.format[Entry]
  implicit def optionFormat[T : Format] : Format[Option[T]] = new Format[Option[T]] {
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) => implicitly[Writes[T]].writes(t)
      case None => Json.obj()
    }
  }



}
