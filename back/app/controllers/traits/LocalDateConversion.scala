package controllers.traits

import java.time.LocalDate

trait LocalDateConversion {
  class Method(s : String)  {
    def toDate(): LocalDate = LocalDate.parse(s)
  }

  implicit def stringToLocalDate(s : String) = new Method(s)
}
