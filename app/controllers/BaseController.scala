package controllers

import services.Services

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{Future, ExecutionContext}
import scala.util.{Try, Success, Failure}

import org.joda.time.DateTime
import play.api.libs.json._
import play.api.mvc._

class BaseController (services: Services, executionContext: ExecutionContext, cc: ControllerComponents) extends AbstractController(cc) {

  implicit val ec = executionContext
  def userService = services.userService

  def withJson[T](f: (T) => Future[Result])
                 (implicit request: Request[AnyContent], format: Reads[T]): Future[Result] = {
    request.body.asJson.orElse(request.body.asText.map(Json.parse)) map { json =>
      json.validate[T] map { parsedValue: T =>
        f(parsedValue)
      } recoverTotal { e =>
        Future.successful(BadRequest("Error Parsing Json: " + JsError.toJson(e)))
      }
    } getOrElse {
      Future.successful(BadRequest("Bad Content-Type"))
    }
  }

  def unauthenticated(f: (Request[AnyContent]) => Future[Result]) = Action.async(f)
}
