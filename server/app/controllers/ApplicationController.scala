package controllers

import play.api._
import play.api.mvc._
import scala.collection.mutable.ListBuffer
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


  def index = Action.async { implicit request => {
    println("index")
    request.session.get("user") match {
      case None => Future {
        Ok(views.html.index(Forms.userForm))
      }
      case Some(userName) => {
        UserService.getUserByName(userName).map {
          _ match {
            case Some(user) => {
              println("Found username while loading index:" + userName)
              Ok(views.html.links()) //?.withSession("user" -> user.userName)
            }
            case None => {
              println("No username found for" + userName)
              Ok(views.html.index(Forms.userForm)) //TODO What is done if user is set in request but not found???
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


  def login = Action.async { implicit request => {
    Forms.userForm.bindFromRequest.fold(
      formWithErrors => Future {
        Redirect(routes.ApplicationController.index)
      },
      //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
      data => {
        UserService.getUser(User(data.name, data.password)).map {
          _ match {
            case Some(user) => {
              println("Login succeeded for: " + data.name)
              Redirect(routes.ApplicationController.index).withSession("user" -> user.name)
            }
            case None => {
              println("Failed login attempt for: " + data.name)
              Redirect(routes.ApplicationController.index) //TODO: How to handle failed user login?
            }
          }
        }
      }
    )
  }
  }


  def addUser = Action.async { implicit request => {
    Forms.userForm.bindFromRequest.fold(
      formWithErrors => Future {
        Status(404)
      },
      //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
      data => {
        if (data.name != data.name.trim) {
          Future {
            Redirect(routes.ApplicationController.index)
          } //TODO Make a page for when new user registration fails.
        } else {
          UserService.addUser(User(data.name, data.password)).map {
            _ match {
              case Some(user) => {
                println("Making new user: " + user.name)
                Redirect(routes.ApplicationController.index).withSession("user" -> user.name)
              }
              case None => {
                println("Could not make new user: " + data.name)
                Ok("TODO") //TODO: Make a page for when new user registration fails.
              }
            }
          }
        }
      }
    )
  }
  }


  def removeUser = Action.async { implicit request => {
    request.session.get("user") match {
      case None => Future {
        Status(404)
      } //TODO How does this even happen?
      case Some(userName) => {
        Forms.userForm.bindFromRequest.fold(
          formWithErrors => Future {
            Status(404)
          },
          //formWithErrors => Future{views.html.???(formWithErrors)}, //TODO Need a page for the user deletion before we can do this.
          data => {
            if (userName != data.name) {
              Future {
                Redirect(routes.ApplicationController.userPage)
              }
            } else {
              UserService.removeUser(User(data.name, data.password)).map {
                _ match {
                  case None => {
                    println("Deleting user: " + data.name)
                    Redirect(routes.ApplicationController.index).withNewSession
                  }
                  case Some(user) => {
                    println("Could not delete user: " + user.name)
                    Status(404) //XXX Couldn't delete user.
                  }
                }
              }
            }
          }
        )
      }
    }
  }
  }

  def userPage = Action.async { implicit request => {
    println("Loading user account page")
    request.session.get("user") match {
      case None => Future {
        Status(404)
      } //Redirect(routes.ApplicationController.index).withNewSession
      case Some(userName) => {
        UserService.getUserByName(userName).map {
          _ match {
            case Some(user) => {
              println("Found username while loading index:" + userName)
              Ok(views.html.userPage(Forms.userForm)).withSession("user" -> user.name)
            }
            case None => {
              println("No username found for" + userName)
              Redirect(routes.ApplicationController.index).withNewSession //TODO What is done if user is set in request but not found???
            }
          }
        }
      }
    }
  }
  }

  def getLink = Action.async { implicit request => {
    request.session.get("user") match {
      case None => Future {
        Status(404)
      } //XXX No user
      case Some(userName) => {
        request.body.asText match {
          case Some(result) => {
            val results = result.split(',')
            val linkData = new LinkData(results(0), results(1), results(2), results(3), results(4), results(5).toInt, results(6))
            LinkService.getLink(Link(userName, linkData.url, linkData.title, linkData.domain, linkData.author, linkData.subreddit, linkData.num_comments, linkData.permalink)).map(
              _ match {
                case Some(link) => Ok(JsBoolean(false)) //Link found in DB.
                case None => Ok(JsBoolean(true)) //Link not found in DB.
              }
            )
          }
          case _ => Future {
            Status(404)
          } //XXX Bad json
        }
      }
      case None => Future {
        Status(404)
      } //XXX No json.
    }
  }}

  def getLinks = Action.async { implicit request => {
    request.session.get("user") match {
      case None => Future {
        Status(404)
      } //XXX No user
      case Some(userName) => {
        request.body.asText match {
          case Some(result) => {
            val resArray = result.split("->")
            var linkList = ListBuffer[LinkData]()
            for (res <- resArray) {
              val results = res.split(',')
              linkList += new LinkData(results(0), results(1), results(2), results(3), results(4), results(5).toInt, results(6))
            }
            val linkDataList = linkList.toSeq
            println("we're in, captain!")
            Future {
              Ok(//TODO: Do we need to call json.stringify on this?
                {
                  JsArray {
                    linkDataList.map(linkData =>
                      Await.result(
                        LinkService.getLink(
                          Link(userName, linkData.url, linkData.title, linkData.domain, linkData.author, linkData.subreddit, linkData.num_comments, linkData.permalink)
                        ).map(
                          _ match {
                            case Some(link_) => JsBoolean(true) //Link found in DB.
                            case None => JsBoolean(false) //Link not found in DB.
                          }
                        ), Duration(1, MINUTES))
                    )
                  }
                }
              )
            }
          }
          case _ => Future {
            Status(404)
          } //XXX Bad json.
        }
      }
      case None => Future {
        Status(404)
      } //XXX No json.
    }
  }}


  def addLink = Action {
    implicit request => {
      request.session.get("user") match {
        case None => {
          println("No user found")
          Status(404)
        } //XXX No user
        case Some(userName) => {
          var res = ""
          request.body.asText match {
            case Some(text) => res = text
            case None => Status(404) //nothing there
          }
          //scala.js json support is broken; forgive me father for I have sinned
          println(res)
          val results = res.split(',')
          val link = new Link(userName, results(0), results(1), results(2), results(3), results(4), results(5).toInt, results(6)) //oh god
          LinkService.addLink(link)
          Ok("")
        }
      }
    }
  }

}