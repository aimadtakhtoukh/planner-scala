package model

import java.time.LocalDate

import model.Availability.Availability

object Availability extends Enumeration {
  type Availability = Value
  val on = Value("ON")
  val off = Value("OFF")
  val maybe = Value("MAYBE")
  val planning = Value("PLANNING")
}
case class User(id : Option[Long], name : String)
case class Entry(id : Option[Long], userId : Long, date : LocalDate, available:  Availability)
case class UserWithEntries(user : User, entries : List[Entry])
