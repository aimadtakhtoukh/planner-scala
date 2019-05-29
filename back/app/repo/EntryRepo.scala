package repo

import java.sql.Date
import java.time.LocalDate

import javax.inject.Inject
import model.Models.Availability.Availability
import model.Models.{Availability, Entry}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.ExecutionContext

class EntryRepo @Inject()
(userRepo: UserRepo)
(implicit ec : ExecutionContext, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  implicit val availabilityMapper =
    MappedColumnType.base[Availability, String](
      e => e.toString,
      s => Availability.withName(s)
    )

  implicit val localDateToDate =
    MappedColumnType.base[LocalDate, Date](
      l => Date.valueOf(l),
      d => d.toLocalDate
    )

  private class Entries(tag : Tag) extends Table[Entry](tag, "entries") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def date = column[LocalDate]("date")
    def available = column[Availability]("available")

    def userId = column[Long]("userId")
    def userFk =
      foreignKey("USER_FK", userId, userRepo.users)(_.id, ForeignKeyAction.Restrict, ForeignKeyAction.Cascade)

    override def * : ProvenShape[Entry] =
      (id.?, userId, date, available) <> (Entry.tupled, Entry.unapply)
  }

  val entries = TableQuery[Entry]


}
