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
      "userName" -> link.userName,
      "data" -> Json.toJson(link.data)
  )
}

//TODO: May need to revamp this method.
implicit val linkReads: Reads[shared.Link] = (
  (JsPath \ "userName").read[String] and
  (JsPath \ "data").read[shared.LinkData]
)(Link.apply _)

class LinkTableDef(tag: Tag) extends Table[Link](tag, "link"){
	//TODO link information
  //TODO make this work...
	
	//Todo override def * =
}

object Links {
  //TODO: Determine how we're going to compare links when fetching from the database.
  
	lazy val links = new TableQuery(tag => new LinkTableDef(tag))
	
  def getLink(link: shared.Link):Future[Option[shared.Link]] = {
    ???
  }

  def addLink(link: shared.Link):Unit = {
    ???
  }
}

