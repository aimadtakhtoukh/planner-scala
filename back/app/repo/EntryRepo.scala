package repo

import java.sql.Date
import java.time.{LocalDate, ZoneId}

import javax.inject.Inject
import model.Availability.Availability
import model.{Availability, Entry, UserWithEntries}
import play.api.Logging
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.lifted._

import scala.concurrent.{ExecutionContext, Future}

class EntryRepo @Inject()
(protected val userRepo: UserRepo)
(implicit ec : ExecutionContext, protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with Logging {
  import profile.api._

  implicit val availabilityMapper: BaseColumnType[Availability] =
    MappedColumnType.base[Availability, String](
      e => e.toString,
      s => Availability.withName(s)
    )

  implicit val localDateToDate: BaseColumnType[LocalDate] =
    MappedColumnType.base[LocalDate, Date](
      l => Date.valueOf(l),
      d => d.toLocalDate
    )

  class Entries(tag : Tag) extends Table[Entry](tag, "entry") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def date = column[LocalDate]("date")
    def dispo = column[Availability]("dispo")

    def userId = column[Long]("user_id")
    def userFk =
      foreignKey("USER_FK", userId, userRepo.users)(_.id, ForeignKeyAction.Restrict, ForeignKeyAction.Cascade)

    override def * : ProvenShape[Entry] =
      (id.?, userId, date, dispo) <> (Entry.tupled, Entry.unapply)
  }

  val entries = TableQuery[Entries]

  def byId(id : Long) : Future[Option[Entry]] =
    db.run(entries.filter(_.id === id).result).map(_.headOption)

  def byUser(id : Long) : Future[List[Entry]] =
    db.run(entries.filter(_.userId === id).result).map(_.toList)

  def add(entry: Entry) : Future[Int] = db.run(entries += entry)

  val joinedQuery =  userRepo.users joinLeft entries on (_.id === _.userId)

  def usersWithEntries(from : LocalDate, to : LocalDate): Future[List[UserWithEntries]] = {
    db.run(
      (userRepo.users joinLeft entries on (_.id === _.userId))
        .filter {case (_, entries) => entries.map(e => e.date >= from && e.date <= to)}
        .result
    )
      .map(
        _
          .groupBy(_._1)
          .mapValues{_.collect {case (_, Some(e)) => e}.toList}
          .map(UserWithEntries.tupled)
          .map(userWithEntries => UserWithEntries(user = userWithEntries.user, entries = userWithEntries.entries.groupBy(_.date).toList.map {case (_, e) => e.last}))
          .toList
          .sortBy(_.user.id)
          .map(u => u.copy(entries = u.entries.sortWith((f, s) => f.date.isBefore(s.date))))
      )
  }

}
