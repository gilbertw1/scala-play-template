package services

import scala.concurrent.ExecutionContext
import javax.inject._

import akka.actor.ActorSystem
import slick.jdbc.PostgresProfile
import play.api.libs.ws.WSClient
import play.api.db.slick.DatabaseConfigProvider
import sql.CustomPostgresProfile
import play.api.Configuration

@Singleton
class Services @Inject() (dbConfigProvider: DatabaseConfigProvider, system: ActorSystem, ws: WSClient) {
  implicit val as = system
  implicit val ec = system.dispatcher

  protected val dbConfig = dbConfigProvider.get[CustomPostgresProfile]
  protected val db = dbConfig.db

  val userService = new UserService(db)
}
