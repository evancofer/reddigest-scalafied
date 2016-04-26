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

//TODO: May need to revamp this method...a lot.
implicit val linkReads: Reads[shared.Link] = (
  (JsPath \ "userName").read[String] and
  (JsPath \ "data").read[shared.LinkData]
)(Link.apply _)

class LinkTableDef(tag: Tag) extends Table[shared.Link](tag, "link"){
  def userName = column[String]("userName")
  def url = column[String]("url")
  def title = column[String]("title")
  val domain = column[String]("domain")
  val author = column[String]("author")
  val subreddit = column[String]("subreddit")
  val num_comments = column[Int]("num_comments")
  val permalink = column[String]("permalink")
	
	override def * = 
	(userName, shared.LinkData(url, title, domain, author, subreddit, num_comments, permalink)) <> (shared.Link.tupled, shared.Link.unapply)
}

object Links {
  //TODO Add try catches etc.
	lazy val links = new TableQuery(tag => new LinkTableDef(tag))
	
  def getLink(link: shared.Link):Future[Option[shared.Link]] = {
    Database.dbConfig.db.run(links.filter(_ === link)).result.headOption
  }

  def addLink(link: shared.Link):Unit = {
    Database.dbConfig.db.run(links += link)
  }
}

