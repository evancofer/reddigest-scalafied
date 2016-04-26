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

case class Link( userName:String,  data:LinkDataLike) extends LinkLike { 
  
  
    def this(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) = {
      this(userName, LinkData(url, title, domain, author, subreddit, num_comments, permalink))
    }
    
        
    implicit val linkWrites : Writes[Link] = (
      (JsPath \ "userName").write[String] and
      (JsPath \ "data").write[LinkData]
    )(unlift(Link.unapply))
    
    
    implicit val linkReads: Reads[Link] = (
      (JsPath \ "userName").read[String] and
      (JsPath \ "data").read[LinkData]
    )(Link.apply _)
    
    
}


