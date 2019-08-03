package models.sql

import java.util.UUID
import org.joda.time.DateTime

case class UserDao(
  id: UUID = UUID.randomUUID,
  username: String,
  firstName: String,
  lastName: String,
  created: DateTime = new DateTime) extends ApiDao
