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

class ApplicationController extends Controller {
  
  def index = Action.async { implicit request =>
    {
      println("index")
      request.session.get("user") match {
        case None => Future { Ok(views.html.index(Forms.userForm)) }
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

}