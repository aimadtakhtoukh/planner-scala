package repo

import javax.inject.Inject
import model.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted._

import scala.concurrent.{ExecutionContext, Future}

class UserRepo @Inject()
  ()
  (implicit ec : ExecutionContext, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "user") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name: Rep[String] = column[String]("name", O.Unique)

    override def * : ProvenShape[User] = (id.?, name) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]

  def all() : Future[List[User]] = db.run(users.result).map(_.toList)

  def byId(id : Long): Future[Option[User]] = db.run(users.filter(_.id === id).result).map(_.headOption)

  def byName(name : String) : Future[Option[User]] =  db.run(users.filter(_.name === name).result).map(_.headOption)

  def add(user : User): Future[Int] = {
    if (user.name.isEmpty) {
      return Future.failed(new IllegalArgumentException("Illegal name"))
    }
    val insertQuery = users returning users.map(_.id) into ((user, id) => user.copy(id = Some(id)))
    db.run(insertQuery += user).map(_.id.map(_.toInt).getOrElse(0))
  }

  def update(id : Long, name : String): Future[Boolean] = {
    val query = for (user <- users if user.id === id) yield user.name
    db.run(query.update(name)) map (_ > 0)
  }
}