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
    getUser(user).map{
       _ match {
         case Some(oldUser) => {
           None
         }
         case None => {
           Database.dbConfig.db.run(users += user)
           Some(user)
         }
       }
    }
  }
	
  def getUser(user: shared.User):Future[Option[shared.User]] = {
    Database.dbConfig.db.run(
        users.filter( aUser =>
            _ === user
        ).result.headOption
    )
  }
  
  def removeUser(user: shared.User):Future[Option[shared.User]] = {
//TODO: Make sure this is the right way to go about remove users from the database, seems unsafe but maybe not?
     Database.dbConfig.db.run(
         users.filter(
             _ === user
         ).delete  
     )
 //TODO: TBH could maybe get away without this return? An exception on failur is definitely not the right answer either however.
     Database.dbConfig.db.run(
         users.filter(
             _ === user 
         ).result.headOption
     )
  }
  


}