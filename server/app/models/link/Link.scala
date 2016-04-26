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

case class Link( userName:String,  data:LinkData) extends LinkLike { 
  
    def this(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) = {
      this(userName, LinkData(url, title, domain, author, subreddit, num_comments, permalink))
    }
    
    def tupled = (userName, data)
    
}

object Link {
    
    // def tupled( arg: (String, LinkData)) = {
    //     Link.apply(arg._1, arg._2).tupled
    // }
    
    def tupled(arg:(String, String, String, String, String, String, Int, String)) = {
        Link.apply(arg._1, LinkData(arg._2, arg._3, arg._4, arg._5, arg._6, arg._7, arg._8)).tupled
    }
    
    // def apply(link: Link) = new Link(link.userName, link.data)
    
    //def apply(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) = new Link(userName,  LinkData(url, title, domain, author, subreddit, num_comments, permalink))
    
    implicit val linkWrites : Writes[Link] = (
      (JsPath \ "userName").write[String] and
      (JsPath \ "data").write[LinkData]
    )(unlift(Link.unapply))
    
    
    implicit val linkReads: Reads[Link] = (
      (JsPath \ "userName").read[String] and
      (JsPath \ "data").read[LinkData]
    )( Link.apply _)
    
}


