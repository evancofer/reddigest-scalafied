package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

import shared._

object UserService {
//Implement UserService like a wrapper for all user actions so that controllers has no dependencies on the implementations such as DB access/registration, and so we can also separate controller from any dependencies on OAuth for reddit account usage.

  def getUserByName(userName:String):User = {"yo"}
  
  def addUser(user: shared.User):Future[Option[shared.User]] = {
    ???
  }
  
  def removeUser(user: shared.User):Future[Option[shared.User]] = {
    ???
  }
}