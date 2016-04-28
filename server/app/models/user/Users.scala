package models
package user

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import shared._

object Users {
  
  
	lazy val users = new TableQuery(tag => new UserTableDef(tag))

	
//TODO: Add exception handling to all of the database queries.
  def addUser(user: User):Future[Option[User]] = {
    getUser(user).map{
       _ match {
         case Some(oldUser) => {
           None
         }
         case None => {
           Models.dbConfig.db.run(users += user)
           Some(user)
         }
       }
    }
  }
	
	
  def getUser(user: User):Future[Option[User]] = {
    Models.dbConfig.db.run(
        users.filter( aUser =>
            (aUser.name === user.name && aUser.password === user.password)
        ).result.headOption
    )
  }

  def getUserByName(userName: String): Future[Option[User]] = {
    Models.dbConfig.db.run(
      users.filter( aUser =>
        (aUser.name === userName)
      ).result.headOption
    )
  }
  
  def removeUser(user: User):Future[Option[User]] = {
//TODO: Make sure this is the right way to go about remove users from the database, seems unsafe but maybe not?
     Models.dbConfig.db.run(
         users.filter( aUser =>
             (aUser.name === user.name && aUser.password === user.password)
         ).delete  
     )
 //TODO: TBH could maybe get away without this return? An exception on failur is definitely not the right answer either however.
     Models.dbConfig.db.run(
         users.filter( aUser =>
             (aUser.name === user.name && aUser.password === user.password)
         ).result.headOption
     )
  }
  
  
}