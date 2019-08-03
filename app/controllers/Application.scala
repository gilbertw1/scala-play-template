package controllers

import services.Services
import models.view.User

import java.util.UUID
import javax.inject.Inject
import scala.async.Async.{async, await}
import scala.concurrent.{Future, ExecutionContext}

import play.api.mvc.ControllerComponents
import play.api.libs.json.Json

import User._

class Application @Inject()(services: Services, executionContext: ExecutionContext, cc: ControllerComponents)
    extends BaseController(services, executionContext, cc) {

  def index() = unauthenticated { implicit request =>
    Future.successful(Ok("I am the play template server, goo goo g'joob!"))
  }

  def getUser(username: String) = unauthenticated { implicit request =>
    services.userService.findByUsername(username).map {
      case Some(user) => Ok(User.create(user).toJson)
      case None => NotFound
    }
  }
}
