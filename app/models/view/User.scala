package models.view

import util.JodaJson._

import java.util.UUID

import models.sql.UserDao
import play.api.libs.json._
import org.joda.time.DateTime

object User extends JsonCompanion[User] {

  implicit val format: Format[User] = Json.format[User]

  def create(userDao: UserDao): User = {
    User(id = userDao.id,
         username = userDao.username,
         firstName = userDao.firstName,
         lastName = userDao.lastName,
         created = userDao.created)
  }
}

case class User(id: UUID,
                username: String,
                firstName: String,
                lastName: String,
                created: DateTime) extends Jsonable[User] {
  val companion = User
}
