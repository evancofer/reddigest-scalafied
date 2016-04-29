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
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.json.JsBoolean

import services.UserService
import services.LinkService
import models.Forms
import models.link.Link
import models.link.LinkData
import models.user.User
import models.Models


class ApplicationController extends Controller {
  
  
  def index = Action {
    Ok(views.html.index())
  }

  def register = Action{ implicit request => {
    //TODO: Take user input then either create account or return an error message
    Ok("okiepokie")
  }
  }
  
  def logout = Action { implicit request => {
      println("Logging out")
      //TODO: Figure out what should be done on logout (i.e. do we need any database interaction?)
      Redirect(routes.ApplicationController.index).withNewSession
    }
  }
  

  def login = Action.async {implicit request => {
      Forms.userForm.bindFromRequest.fold(
          formWithErrors => Future{Status(404)},
          //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
          data => {
            UserService.getUser( User(data.name, data.password) ).map{
              _ match {
                case Some(user) =>{
                  println("Login succeeded for: " + data.name)
                  Redirect(routes.ApplicationController.index).withSession("user"->user.name)
                }
                case None => {
                  println("Failed login attempt for: "+data.name)
                   Status(404) //TODO: How to handle failed user login?
                }
              }
            }
          }
      )
  }}

  
  def addUser = Action.async { implicit request => {
      Forms.userForm.bindFromRequest.fold(
          formWithErrors => Future{Status(404)},
          //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
          data => {
                UserService.addUser(User(data.name, data.password)).map{
                   _ match {
                    case Some(user) => {
                      println("Making new user: "+user.name)
                      Redirect(routes.ApplicationController.index).withSession("user"->user.name)
                    }
                    case None => {
                      println("Could not make new user: "+data.name)
                       Ok("TODO") //TODO: Make a page for when new user registration fails.
                    }
                  }
                }
          }
        )
    }}
  
  
  def removeUser = Action.async { implicit request => {
      Forms.userForm.bindFromRequest.fold(
          formWithErrors => Future{Status(404)},
          //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
          data => {
              UserService.removeUser(User(data.name, data.password)).map{
                 _ match {
                  case None => {
                    println("Deleting user: "+data.name)
                    Redirect(routes.ApplicationController.index).withNewSession
                  }
                  case Some(user) => {
                    println("Could not delete user: "+user.name)
                    Status(404) //XXX Couldn't delete user.
                  }
                }
              }
          }
        )
    }}
  
  def userPage = Action.async {implicit request => {
      println("Loading user account page")
      request.session.get("user") match {
        case None => Future{Status(404)}//Redirect(routes.ApplicationController.index).withNewSession
        case Some(userName) => {
          UserService.getUserByName(userName).map {
            _ match {
              case Some(user) => {
                println("Found username while loading index:" + userName)
                Ok(views.html.userpage()).withSession("user"->user.name)
              }
              case None => {
                println("No username found for" + userName)
                Redirect(routes.ApplicationController.index).withNewSession//TODO What is done if user is set in request but not found???
              }
            }
          }
        }
      }
  }}
  
  def getLink = Action.async { implicit request => {
    request.session.get("user") match {
      case None => Future{Status(404)} //XXX No user
      case Some(userName) => {
         request.body.asJson match {
            case Some(json) => {
                Json.fromJson[LinkData](json) match {
                    case linkData: JsSuccess[LinkData] => {
                        LinkService.getLink(Link(userName, linkData.get.url, linkData.get.title, linkData.get.domain, linkData.get.author, linkData.get.subreddit, linkData.get.num_comments, linkData.get.permalink)).map(
                            _ match {
                                case Some(link) => Ok(JsBoolean(false))//Link found in DB.
                                case None => Ok(JsBoolean(true))//Link not found in DB.
                            }
                        )
                    }
                    case _ => Future{Status(404)}//XXX Bad json
                }
            }
            case None => Future{Status(404)}//XXX No json.
         }
      }
    }
  }}
  
  def getLinks = Action.async{ implicit request => {
    request.session.get("user") match {
      case None => Future{Status(404)} //XXX No user
      case Some(userName) => {
         request.body.asJson match {
            case Some(json) => {
                Json.fromJson[List[LinkData]](json) match {
                    case linkDataList: JsSuccess[Seq[LinkData]] => { Future{Ok(//TODO: Do we need to call json.stringify on this?
                       JsArray{ linkDataList.get.map( linkData =>
                          
                         Await.result(
                         LinkService.getLink(
                              Link(userName, linkData.url, linkData.title, linkData.domain, linkData.author, linkData.subreddit, linkData.num_comments, linkData.permalink)
                              ).map(
                                  _ match {
                                    case Some(link_) => JsBoolean(false)//Link found in DB.
                                    case None => JsBoolean(true) //Link not found in DB.
                                  }
                              ), Duration(1, MINUTES))
                              
                              
                        )}
                    )}}
                    case _ => Future{Status(404)}//XXX Bad json.
                }
            }
            case None => Future{Status(404)}//XXX No json.
         }
      }
    }
  }}
  

  def addLink = Action { implicit request => {
    request.session.get("user") match {
      case None =>  Status(404) //XXX No user
      case Some(userName) => {
         request.body.asJson match {
            case Some(json) => {
                Json.fromJson[Link](json) match {
                    case link: JsSuccess[Link] =>{
                        LinkService.addLink(link.get)
                        Ok("Todo")
                    }
                    case _ => Status(404)//XXX Bad json
                }
            }
            case None => Status(404)//XXX No json
         }
      }
    }
  }}
  
  
}