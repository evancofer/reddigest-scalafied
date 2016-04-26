package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import shared.User //XXX I think this is how I should do this.



class UserTableDef(tag: Tag) extends Table[User](tag, "user"){
	def name = column[String]("name")
	def password = column[String]("password")
	
	override def * = 
	(name, password) <> (User.tupled, User.unapply)
}

object Users {
	lazy val users = new TableQuery(tag => new UserTableDef(tag))

  def addUser(user: shared.User):Future[Option[shared.User]] = {
    ???
  }
  
  def removeUser(user: shared.User):Future[Option[shared.User]] = {
    ???
  }
  
  def getUser(user: shared.User):Future[Option[shared.User]] = {
    ???
  }

}