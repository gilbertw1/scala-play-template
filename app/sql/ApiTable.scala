package sql

import models.sql.ApiDao
import java.util.UUID

import CustomPostgresProfile.api._

abstract class ApiTable[T <: ApiDao](tag: Tag, tname: String) extends Table[T](tag, tname) {
  def id = column[UUID]("id", O.PrimaryKey)
}
