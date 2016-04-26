package models
package link

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
import play.api.libs.json.Reads._
import play.api.libs.json.Writes._

import shared._

case class Link(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) extends LinkLike { 
    def data:LinkData = LinkData(url, title, domain, author, subreddit, num_comments, permalink)
}

object Link {
    
    def tupled = (Link.apply _).tupled
    
    implicit val linkWrites = new Writes[Link] {
    def writes(link:Link) = Json.obj(
        "userName" -> link.userName,
        "data" -> Json.toJson(link.data)
        )
    }
    
    
    implicit val linkReads: Reads[Link] = (
      (JsPath \ "userName").read[String] and
      (JsPath \ "data" \ "url").read[String] and
      (JsPath \ "data" \ "title").read[String] and
      (JsPath \ "data" \ "domain").read[String] and
      (JsPath \ "data" \ "author").read[String] and
      (JsPath \ "data" \ "subreddit").read[String] and
      (JsPath \ "data" \ "num_comments").read[Int] and
      (JsPath \ "data" \ "permalink").read[String]
    )( Link.apply _)
    
}


