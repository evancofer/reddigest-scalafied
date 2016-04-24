package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

object UserService {
//Implement UserService like a wrapper for all user actions so that controllers has no dependencies on the implementations such as DB access/registration, and so we can also separate controller from any dependencies on OAuth for reddit account usage.


}