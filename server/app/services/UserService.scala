package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

import shared._
import models._

object UserService {

  def addUser(user: User):Future[Option[User]] = {
    Users.addUser(user)
  }
  
  def removeUser(user: User):Future[Option[User]] = {
    Users.removeUser(user)
  }
  
  def getUser(user: User):Future[Option[User]] = {
    Users.getUser(user)
  }
  
  def getUserByName(userName: String):Future[Option[User]] = {
      ???
  }
}