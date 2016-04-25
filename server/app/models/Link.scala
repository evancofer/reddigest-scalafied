package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json._
import play.api.libs.functional.syntax._

import shared._

implicit val linkWrites = new Writes[shared.Link] {
  def writes(link: shared.Link) = Json.obj(
      "data" -> Json.toJson(link.data)
  )
}

implicit val linkReads: Reads[shared.Link] = (
  (JsPath \ "data").read[shared.LinkData]
)(Link.apply _)

class LinkTableDef(tag: Tag) extends Table[Link](tag, "link"){
	//TODO link information
  //TODO make this work...
	
	//Todo override def * =
}

object Links {
	lazy val links = new TableQuery(tag => new LinkTableDef(tag))
}

