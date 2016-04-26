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

case class LinkData( url:String,  title:String,  domain:String,  author:String,  subreddit:String,  num_comments:Int,  permalink:String) extends LinkDataLike{
     

}

object LinkData {
    
    implicit val linkDataWrites : Writes[LinkData] = (
      (JsPath \ "url").write[String] and
      (JsPath \ "title").write[String] and
      (JsPath \ "domain").write[String] and
      (JsPath \ "author").write[String] and
      (JsPath \ "subreddit").write[String] and
      (JsPath \ "num_comments").write[Int] and
      (JsPath \ "permalink").write[String]
    )(unlift(LinkData.unapply))
    
    
    implicit val linkDataReads: Reads[LinkData] = (
      (JsPath \ "url").read[String] and
      (JsPath \ "title").read[String] and
      (JsPath \ "domain").read[String] and
      (JsPath \ "author").read[String] and
      (JsPath \ "subreddit").read[String] and
      (JsPath \ "num_comments").read[Int] and
      (JsPath \ "permalink").read[String]
    )(LinkData.apply _)
    
}