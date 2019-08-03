package models.view

import play.api.libs.json._

abstract class JsonCompanion[T <: AnyRef] {
  implicit val format: Format[T]

  def fromJson(json: String): T = Json.parse(json).as[T]
  def toJson(model: T): JsValue = Json.toJson(model)
  def fromJsonArray(json: String) = Json.parse(json).as[Seq[T]]
  def toJsonArray(model: Seq[T]): JsValue = Json.toJson(model)
  def patch(model: T, jsObj: JsObject): T = (Json.toJson(model).as[JsObject] ++ jsObj).as[T]
  def toJsObject(model: T): JsObject = Json.toJson(model).as[JsObject]
  def fromJsObject(obj: JsObject): T = obj.as[T]

  def toJsonWithProps(model: T, props: (String, Any)*): JsValue = {
    var jobj = Json.toJson(model).asInstanceOf[JsObject]
    props.foreach { case (k,v) =>
      val jsval = toJsonType(v)
      if(jsval.isDefined)
        jobj += (k -> jsval.get)
    }
    jobj
  }

  def toJsonType(obj: Any): Option[JsValue] = {
    obj match {
      case None =>
        None
      case Some(o: Any) =>
        toJsonType(o)
      case x: String =>
        Some(JsString(x))
      case x: Long =>
        Some(JsNumber(x))
      case x: Int =>
        Some(JsNumber(x))
      case x: Boolean =>
        Some(JsBoolean(x))
      case x: Jsonable[_] =>
        Some(x.toJson)
      case x: JsValue =>
        Some(x)
      case _ =>
        throw new Exception(s"Unrecognized json primitive type")
    }
  }
}
