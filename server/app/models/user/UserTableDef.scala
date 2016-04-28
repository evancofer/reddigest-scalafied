package models
package user


import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import shared._

class UserTableDef(tag: Tag) extends Table[User](tag, "user"){
	def userid = column[Int]("userid", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
	def password = column[String]("password")
	
	override def * = 
	(name, password) <> (User.tupled, User.unapply)
}
