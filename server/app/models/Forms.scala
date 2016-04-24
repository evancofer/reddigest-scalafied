package models

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

object Forms {
	val userForm = Form (
		mapping(
			"name"->nonEmptyText,
			"password"->nonEmptyText//TODO change this to password probs.
		)(User.apply)(User.unapply)
	)
	
//TODO user registration form.

}