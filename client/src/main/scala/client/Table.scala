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

import shared.Link

	/*
	 * Immediately download links from reddit
	 * Do ajax call to server whenever links are exited out of to tell server we've seen that link.
	 */

@JSExport
object Table { //DOES THIS HAVE TO EXTEND JSAPP?
  val domTarget:???//TODO Dom element for where we will place the table.
  
  val rows:Array[TableRow](25)
  
  //TODO array holding references to the html content of the individual rows in the DOM, so we can access/change them easily.
  
  def load(n:Int):shared.Link = {//TODO loads a new set of links from reddit that has n links in it.
    
  }
  
  @JSExport
  def render():Unit = {//TODO Creates the initial table
    /*
			Call load(rowCount)
			Somehow get the stuff loaded in and put it in the table.	
     */
  }
  
  @JSExport
  def refreshRow(n:Int):Unit = {//TODO Make it so this can also take a javascript dynamic and cast to int.
    if(n < 0 || n >= rows.size){
      return 
    } else {
      this.rows(n).setLink(
          this.load(1)
      )
      
    /*
     Get link that we need to get rid of, send ajax to server to add it to seen list.
     Get a new link from reddit.
     Set existing link to be that new link.
     Go to DOM object for that link, and change SOME of the parameters to change the link, dont just refresh whole thing.
     */
    }
  }
  
}