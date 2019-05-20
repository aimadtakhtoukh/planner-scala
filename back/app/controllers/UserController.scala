package controllers

import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import play.api.mvc._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile

import scala.concurrent.{Await, ExecutionContext}


@Singleton
class UserController @Inject()
    (protected val dbConfigProvider : DatabaseConfigProvider, cc: ControllerComponents)
    (implicit ec : ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  case class User(id : Long, name : String)
  case class LiftedUser(id : Rep[Long], name : Rep[String])
  implicit object UserShape extends CaseClassShape(LiftedUser.tupled, User.tupled)

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey)
    def name = column[String]("name")

    override def * = LiftedUser(id, name)
  }

  val users = TableQuery[Users]
  implicit val userFormats: OFormat[User] = Json.format[User]

  def getById(id : Long): Action[AnyContent] = Action.async {
    implicit request =>
      db.run(users.filter(_.id === id).result)
        .map(_.headOption)
        .map {
          case Some(user) => user
          case None => throw new IllegalStateException("User not defined.")
        }
        .map(Json.toJson)
        .map(Ok(_))
  }

}
