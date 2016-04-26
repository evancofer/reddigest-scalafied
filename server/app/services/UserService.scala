package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

import shared._
import models._

object UserService {

  def addUser(user: shared.User):Future[Option[shared.User]] = {
    Users.addUser(user)
  }
  
  def removeUser(user: shared.User):Future[Option[shared.User]] = {
    Users.removeUser(user)
  }
  
  def getUser(user: shared.User):Future[Option[shared.User]] = {
    Users.getUser(user)
  }
}