package repo

import javax.inject.Inject
import model.Models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted._

import scala.concurrent.{ExecutionContext, Future}

class UserRepo @Inject()
  ()
  (implicit ec : ExecutionContext, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  case class LiftedUser(id : Rep[Long], name : Rep[String])
  implicit object UserShape extends CaseClassShape(LiftedUser.tupled, User.tupled)

  private class Users(tag: Tag) extends Table[User](tag, "users") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey)
    def name: Rep[String] = column[String]("name")

    override def * : ProvenShape[User] = LiftedUser(id, name)
  }

  private val users = TableQuery[Users]

  val setup = DBIO.seq(users.schema.create, users += User(1, "Aimad"))
  val setupFuture: Future[Unit] = db.run(setup)

  def getById(id : Long): Future[Option[User]] = db.run(users.filter(_.id === id).result).map(_.headOption)

  def add(user : User): Future[Int] = db.run(users += user)
}