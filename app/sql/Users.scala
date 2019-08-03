package sql

import models.sql.UserDao

import java.util.UUID
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._
import CustomPostgresProfile.api._

object Users extends SqlCompanion[UserDao, Users](TableQuery[Users]) {
  val USER = tquery
}

class Users(tag: Tag) extends ApiTable[UserDao](tag, "app_user") {
  def username = column[String]("username")
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def created = column[DateTime]("created")

  def * = (id, username, firstName, lastName, created) <> ((UserDao.apply _).tupled, UserDao.unapply)
}
