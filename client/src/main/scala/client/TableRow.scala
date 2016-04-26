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

class TableRow(private var link:Link, val rowNumber:Int, private val html:String){//change this eventually yo to html:HTMLTableRowElement
  //TODO on construction automatically add content to the DOM element.
  
  def refresh():Unit = {
    link = TableRow.load()//TODO take the new link and do something with it.
    //TODO re-render parts of the DOM that actually need changing.
  }
  
  def articleLink:String = {
    link.data.url.replace("""\/""", """/""")
  }
  
  def articleTitle:String = {
    link.data.title
  }
  
  def articleSiteName:String = {
    link.data.domain
  }
  
  def posterName:String = {
    link.data.author
  }
  
  def posterLink:String = {
    """https://www.reddit.com/user/"""+link.data.author
  }
  
  def subredditName:String = {
    """/r/"""+link.data.subreddit
  }
  
  def subredditLink:String = {
    """https://www.reddit.com/r/"""+link.data.subreddit
  }
  
  def commentCount:Int = {
    link.data.num_comments
  }
  
  def commentLink:String = {
    """https://www.reddit.com"""+link.data.permalink.replace("""\/""", """/""")
  }
  
  def asHtml:String = {
//    """<tr>"""+{
         """<div class="col-md-12">"""+{
           """div id="sub_row" class="row">"""+{
             """<div class="col-sm-12"  style="font-size: 16px; vertical-align: middle;">"""+{
               """<a id="link_seen_button" href="javascript:client.TableRow().refreshRow("""+rowNumber+""")">"""+{
                 """&nbsp;<span class="glyphicon glyphicon-remove-sign" style="color: white;"></span>&nbsp;"""
                 }+"""</a>"""+{
                   """<a id="the_link" href=""""+this.articleLink+"""">"""+this.articleTitle+"""</a>"""+"""&nbsp;<a href=http://""""+this.articleSiteName+"""" style="font-size: 12px;">["""+this.articleSiteName+"""]</a>""" 
                 }
             }+"""</div>"""
           }+"""</div>"""
         }+"""</div>"""+"""<div class="row">"""+{
            """<div class="col-sm-12 col-xs-offset-1">"""+{
                  """by """+{
                    """<a href=""""+this.posterLink+""""">"""+this.posterName+"""</a>"""
                  }+"""@"""+{
                    """<a href=""""+this.subredditLink+""""">"""+this.subredditName+"""</a>"""
                  }+"""|"""+{
                    """<a href=""""+this.commentLink+"""">"""+this.commentCount.toString+""" comments</a>"""
                  }
            }+"""</div>""" 
          }+"""</div>"""
//    }+"""</tr>"""
  }
}

	/*
	 * Immediately download links from reddit
	 * Do ajax call to server whenever links are exited out of to tell server we've seen that link.
	 */
@JSExport
object TableRow {
  //TODO Keep a link source object that allows us to do the buffered checks and only pull out one at a time?
  
  //TODO: Create the table in the dom with the rows, and pass the row references to new tablerows
  //val rows:Array[TableRow]
  
  def initialize():Unit = {
    ???
  }
  
  
  def load(n:Int):Seq[Link] = {//TODO loads a new set of links from reddit that has n links in it?
    ???
  }
  
  def load():Link = {
    //Just loads a single link.
    ???
  }

  
  @JSExport
  def refreshRow(n:Int):Unit = {//TODO Make it so this can also take a javascript dynamic and cast to int!
    if(n < 0 || n >= 5){//rows.size instead of 5
      return 
    } else {
      //this.rows(n).refresh()
    }
  }
  
}
//TODO: Will we keep a "seen" array on local storage? Think more about this, probably need to build an object for it, or modify the TableRow companion object.
