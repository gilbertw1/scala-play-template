package models.view

import play.api.libs.json._

trait Jsonable[Self <: AnyRef] { this: Self =>
  def companion: JsonCompanion[Self]

  def toJsonWithProps(props: (String, Any)*): JsValue = companion.toJsonWithProps(this, props:_*)
  def toJson(): JsValue = companion.toJson(this)
  def patch(jsObj: JsObject): Self = companion.patch(this, jsObj.asInstanceOf[JsObject])
  def toJsObject(): JsObject = companion.toJsObject(this)
}
