package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Json
import play.api.libs.json.JsArray
import play.api.libs.json.JsValue
import play.api.libs.json.JsString
import scala.concurrent.Await

import services.UserService
import services.LinkService
import shared.User

class ApplicationController extends Controller {
  
  def index = Action.async { implicit request => {
      println("index")
      request.session.get("user") match {
        case None => Future {Ok(views.html.index(Forms.userForm))}
        case Some(userName) => {
          UserService.getUserByName(userName).map {
            _ match {
              case Some(user) => {
                println("Found username while loading index:" + userName)
                links_ => Ok(views.html.links).withSession("user" -> user.name)
              }
              case None => {
                println("No username found for" + userName)
                Ok(views.html.index(Forms.userForm))
              }
            }
          }
        }
      }
    }
  }

  
  def logout = Action { implicit request => {
      println("Logging out")
      //TODO: Figure out what should be done on logout (i.e. do we need any datbase interraction?)
      Redirect(routes.ApplicationController.index).withNewSession
    }
  }
  

  def login = Action.async {implicit request => {
      Forms.userForm.bindFromRequest.fold(
          formWithErrors => Future{Redirect(routes.ApplicationController.index)},
          data => {
            UserService.getUser( User(data.name, data.password) ).map{
              _ match {
                case Some(user) =>{
                  println("Login succeeded for: "+data.name)
                  Redirect(routes.ApplicationController.index).withSession("user"->user.name)
                }
                case None => {
                  println("Failed login attempt for: "+data.name)
                  ??? //TODO: Make a page for when user login fails.
                }
              }
            }
          }
      )
  }}

  
  def addUser = Action.async { implicit request => {
      Forms.userForm.bindFromRequest.fold(
          errorForm => Future{Redirect(routes.ApplicationController.index)},
          data => {
                UserService.addUser(User(data.name, data.password)).map{
                   _ match {
                    case Some(user) => {
                      println("Making new user: "+user.name)
                      Redirect(routes.ApplicationController.index).withSession("user"->user.name)
                    }
                    case None => {
                      println("Could not make new user: "+user.name)
                      ??? //TODO: Make a page for when new user registration fails.
                    }
                  }
                }
          }
        )
    }}
  
  
  def removeUser = Action.async { implicit request => {
      Forms.userForm.bindFromRequest.fold(
          errorForm => Future{Redirect(routes.ApplicationController.index)},//TODO: Make a page/response for when user deletion fucks up.
          data => {
              UserService.removeUser(User(data.name, data.password)).map{
                 _ match {
                  case Some(user) => {
                    println("Deleting user: "+user.name)
                    Redirect(routes.ApplicationController.index).withNewSession
                  }
                  case None => {
                    println("Could not delete user: "+user.name)
                    ??? //TODO: Make a page for when...how do we even get here?
                  }
                }
              }
          }
        )
    }}
  
  
  def checkLink = ???
  
  
  def storeLink = ???
    /*
   * checkLink should check if a new link from user is already in the db. Maybe do it like 25 links at a time?
   */
}