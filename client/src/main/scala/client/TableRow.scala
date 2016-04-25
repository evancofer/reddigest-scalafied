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

class TableRow(var link:shared.Link, val rowNumber:Int){
  
  def setLink(newLink:shared.Link):Unit = {
    link = newLink
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
    """<tr>"""+{
         """<div class="col-md-12">"""+{
           """div id="sub_row" class="row">"""+{
             """<div class="col-sm-12"  style="font-size: 16px; vertical-align: middle;">"""+{
               """<a id="link_seen_button" href="javascript:Table.refreshRow("""+rowNumber+""")">"""+{
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
    }+"""</tr>"""
  }
}
