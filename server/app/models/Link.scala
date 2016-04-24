package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import shared.Link

class LinkTableDef(tag: Tag) extends Table[Link](tag, "link"){
	//TODO link information
	
	//Todo override def * =

}




object Links {
	lazy val links = new TableQuery(tag => new LinkTableDef(tag))

}