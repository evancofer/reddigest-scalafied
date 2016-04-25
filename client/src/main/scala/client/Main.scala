package client

import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.JSApp
import scala.collection.mutable
import scala.collection.mutable.MutableList
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import dom.html
import dom.ext._
import scala.scalajs.js.timers._
import dom.ext.Ajax
import scala.scalajs.js.JSON
import upickle.default._
import scala.concurrent.ExecutionContext.Implicits.global

import shared.User
import shared.Link

@JSExport
object Main extends JSApp {
	
	@JSExport
	def main():Unit = {
	
	/*
	 * Immediately download links from reddit
	 * Do ajax call to server whenever links are exited out of to tell server we've seen that link.
	 */
	
	}
}