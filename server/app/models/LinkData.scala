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

implicit val linkDataWrites = new Writes[shared.LinkData] {
  def writes(data: shared.LinkData) = Json.obj(
          "url" -> data.url,
          "title" -> data.title,
          "domain" -> data.domain,
          "author" -> data.author,
          "subreddit" -> data.subreddit,
          "num_comments" -> data.num_comments,
          "permalink" -> data.permalink
  )
}

implicit val linkDataReads: Reads[shared.LinkData] = (
  (JsPath \ "url").read[String] and
  (JsPath \ "title").read[String] and
  (JsPath \ "domain").read[String] and
  (JsPath \ "author").read[String] and
  (JsPath \ "subreddit").read[String] and
  (JsPath \ "num_comments").read[Int] and
  (JsPath \ "permalink").read[String]
)(LinkData.apply _)

