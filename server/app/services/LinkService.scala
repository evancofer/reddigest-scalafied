package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

import models.link._

object LinkService {
//Implement linkservice like a wrapper around all link operations, so views/controller have no direct dependency on link downloading/database implementation.
  
  def getLink(link: Link):Future[Option[Link]] = {
    Links.getLink(link)
  }

  def addLink(link: Link):Unit = {
    Links.addLink(link)
  }
  
  

}