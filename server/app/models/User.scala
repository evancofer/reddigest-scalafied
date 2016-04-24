package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import {shared.User => User} //XXX I think this is how I should do this.



class UserTableDef(tag: Tag) extends Table[User](tag, "user"){
	def name = column[String]("name")
	def password = column[String]("password")
	
	override def * = 
	(name, password) <> (User.tupled, User.unapply)
}

object Users {
	lazy val users = new TableQuery(tag => new UserTableDef(tag))


/*
TODO:	Need to update this stuff:
    
	def getUserByName(userName: String) : Future[Option[User]]
	
    def getUser( user: User): Future[Option[User]] 
    
    def addUser(user: User):Future[Option[User]]
*/

}