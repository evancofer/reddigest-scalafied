package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import shared._

object Forms {
  //TODO: Change the password from nonEmptyText
	val userForm = Form (
		mapping(
			"username"->nonEmptyText,
			"password"->nonEmptyText
		)(user.User.apply)(user.User.unapply)
	)
	
//TODO user registration form.

}