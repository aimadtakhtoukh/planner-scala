package security

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repo.UserRepo
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

case class SecurityUser(id : Option[Long] = None, userId : Long, securityId : String, `type` : String)

class SecurityUserRepo @Inject()
(protected val userRepo: UserRepo)
(implicit ec : ExecutionContext, protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  class SecurityUsers(tag : Tag) extends Table[SecurityUser](tag, "security_user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def securityId = column[String]("security_id")
    def origin = column[String]("type")

    def userId = column[Long]("user_id")
    def userFk =
      foreignKey("USER_FK", userId, userRepo.users)(_.id, ForeignKeyAction.Restrict, ForeignKeyAction.Cascade)

    override def * : ProvenShape[SecurityUser] =
      (id.?, userId, securityId, origin) <> (SecurityUser.tupled, SecurityUser.unapply)

  }

  val securityUsers = TableQuery[SecurityUsers]

  def byId(id : Long) : Future[Option[SecurityUser]] =
    db.run(securityUsers.filter(_.id === id).result).map(_.headOption)

  def add(securityUser: SecurityUser) : Future[Int] = db.run(securityUsers += securityUser)

  def bySecurityId(id : String) : Future[Option[SecurityUser]] =
    db.run(securityUsers.filter(_.securityId === id).result).map(_.headOption)

}
