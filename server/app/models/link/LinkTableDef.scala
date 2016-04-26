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

import shared._

class LinkTableDef(tag: Tag) extends Table[Link](tag, "link"){
  def userName = column[String]("userName")
  def url = column[String]("url")
  def title = column[String]("title")
  def domain = column[String]("domain")
  def author = column[String]("author")
  def subreddit = column[String]("subreddit")
  def num_comments = column[Int]("num_comments")
  def permalink = column[String]("permalink")
	
    override def * = 
    (userName, url, title, domain, author, subreddit, num_comments, permalink) <> (Link.tupled, Link.unapply)
}