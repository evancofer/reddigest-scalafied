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

object Links {
  
  //TODO Add try-catches etc.
  lazy val links = new TableQuery(tag => new LinkTableDef(tag))
	
	
  def getLink(link: Link):Future[Option[Link]] = {
    Models.dbConfig.db.run(links.filter(aLink => 
     aLink.userName === link.userName && (aLink.url === link.data.url || aLink.title === link.data.title)
    ).result.headOption)
  }

	
  def addLink(link: Link):Unit = {
    Models.dbConfig.db.run(links += link)
  }
  
}