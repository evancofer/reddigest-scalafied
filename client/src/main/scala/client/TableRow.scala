package client

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Await}
import scala.scalajs.js
import scala.collection.mutable.{ArrayBuffer, MutableList, ListBuffer}
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class TableRow(private var link:Link, val rowNumber:Int){//change this eventually yo to html:HTMLTableRowElement
  //TODO on construction automatically add content to the DOM element.
  
  def refresh():Unit = {
    //link = TableRow.load()//TODO take the new link and do something with it.
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
    """<tr><td>"""+{
         """<div class="col-md-12">"""+{
           """<div id="sub_row" class="row">"""+{
             """<div class="col-sm-12"  style="font-size: 16px; vertical-align: middle;">"""+{
               """<a id="link_seen_button" href="javascript:client.TableRow().refreshRow("""+rowNumber+""")">"""+{
                 """&nbsp;<span class="glyphicon glyphicon-remove-sign" style="color: orange;"></span>&nbsp;"""
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
    }+"""</td></tr>"""
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
  val domTable = dom.document.getElementById("linkTableBody")
  var links = ArrayBuffer[Link]()

  def initialize():Unit = {
     initialLoad(50)
  }
  
  
  def initialLoad(n:Int):Unit = {//TODO loads a new set of links from reddit that has n links in it?
    val url = "http://www.reddit.com/r/all.json?limit="+n
    var links = ArrayBuffer[Link]()
    Ajax.get(url).onSuccess {
      case xhr =>
        if(xhr.status == 200) {
          js.JSON.parse(xhr.responseText) match {
            //Can't get Futures to play nice in Scala.js
            //just gonna do it all in one thread here ayyy
            case json: js.Dynamic =>
              links = parseLinks(json)
              var j = 0
              for (link <- links) {
                domTable.innerHTML = domTable.innerHTML + new TableRow(link, j).asHtml
                j += 1
              }
              println(links.length)
              setLinks(links)
            case _ => println("JSON not found for url load request" + xhr.responseText)
          }
        }
      case _ => println("Non-200 status code!")
    }
  }

  def setLinks(links: ArrayBuffer[Link]){
    this.links = links
  }

  def loadNewLink(n: Int):Unit = {
    val url = "http://www.reddit.com/r/all.json?limit="+1
    val ajaxRes = Ajax.get(url).onSuccess {
      case xhr =>
        if(xhr.status == 200){
          val json = js.JSON.parse(xhr.responseText)
          links(n) = parseLinks(json)(0)
          println("links n "+ links(n))
          println("I think this is the right link: \n: "+domTable.children.item(n).innerHTML)

          domTable.children.item(n).innerHTML = new TableRow(links(n),n).asHtml
        }
    }
  }

  def parseLinks(json: js.Dynamic):ArrayBuffer[Link] = {
    val dataObj = json.data
    val links = dataObj.children.asInstanceOf[js.Array[js.Dynamic]]
    var retLinks = new ArrayBuffer[Link]
    for(link <- links) {
      val data = link.data
      var article_link = data.url.asInstanceOf[String]
      var article_title = data.title.asInstanceOf[String]
      val article_site_name = data.domain.asInstanceOf[String]
      val poster_name = data.author.asInstanceOf[String]
      //val poster_link = "https://www.reddit.com/user/" + poster_name
      val subreddit_name = data.subreddit.asInstanceOf[String]
      //val subreddit_link = "https://www.reddit.com" + subreddit_name
      val comment_count = data.num_comments.asInstanceOf[Int]
      var comment_link = data.permalink.asInstanceOf[String]

      if (article_title.length() > 165) {
        article_title = article_title.substring(0, 160)
        article_title += "..."
      }
      article_link = article_link.replace("\\/", "/")
      comment_link = comment_link.replace("\\/", "/")
      retLinks += Link(poster_name, LinkData(article_link, article_title, article_site_name, poster_name, subreddit_name, comment_count, comment_link))
    }
    retLinks
  }

  @JSExport
  def refreshRow(dyn: js.Dynamic):Unit = {//TODO Make it so this can also take a javascript dynamic and cast to int!
    println("hello")
    val n = dyn.asInstanceOf[Int]
    //println(n)
    println("beg links size "+links.size)
    if(n < 0 || n >= links.size){
      //rows.size instead of 5
      println("n too big!")
      return
    } else {
      loadNewLink(n)
    }
    println("end links size"+links.size)
  }
  
}
//TODO: Will we keep a "seen" array on local storage? Think more about this, probably need to build an object for it, or modify the TableRow companion object.
