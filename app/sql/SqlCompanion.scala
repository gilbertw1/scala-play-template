package sql

import models.sql.ApiDao
import CustomPostgresProfile.api._

abstract class SqlCompanion[T <: ApiDao, U <: ApiTable[T]](val tquery: TableQuery[U]) {
  val tqueryWithId = tquery returning tquery.map(_.id)
  def self(): SqlCompanion[T, U] = this
}
