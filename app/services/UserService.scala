package services

import models.sql.UserDao
import sql.{Users, CustomPostgresProfile}

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

import CustomPostgresProfile.api._
import org.joda.time.DateTime

import Users._

class UserService(database: Database)(implicit ec: ExecutionContext) extends DatabaseService(Users, database) {

  def findByUsername(username: String): Future[Option[UserDao]] = {
    db.run(USER.filter(_.username === username).result.headOption)
  }
}
