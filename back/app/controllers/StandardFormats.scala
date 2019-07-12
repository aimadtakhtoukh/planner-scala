package controllers

import model.{Availability, Entry, User, UserWithEntries}
import play.api.libs.json._
import security.{DiscordUser, SecurityUser}

trait StandardFormats {

  implicit val userFormats: OFormat[User] = Json.format[User]
  implicit val disponibilityFormats: Format[Availability.Value] = Json.formatEnum(Availability)
  implicit val entryFormats: OFormat[Entry] = Json.format[Entry]
  implicit val userWithEntriesFormats : OFormat[UserWithEntries] = Json.format[UserWithEntries]
  implicit val securityUserFormats : OFormat[SecurityUser] = Json.format[SecurityUser]
  implicit val discordUserFormats : OFormat[DiscordUser] = Json.format[DiscordUser]

  implicit def optionFormat[T : Format] : Format[Option[T]] = new Format[Option[T]] {
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) => implicitly[Writes[T]].writes(t)
      case None => Json.obj()
    }
  }
}
