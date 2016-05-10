package client

import org.scalajs.dom.html.Html

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.scalajs.js
import scala.collection.mutable.{ArrayBuffer}
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

class TableRow(private var link: Link, val rowNumber: Int, val hidden: Boolean) {

  def this(link: Link, row: Int) = this(link, row, false)

  //change this eventually yo to html:HTMLTableRowElement
  //TODO on construction automatically add content to the DOM element.

  def refresh(): Unit = {
    //link = TableRow.load()//TODO take the new link and do something with it.
    //TODO re-render parts of the DOM that actually need changing.
  }

  def articleLink: String = {
    link.data.url.replace( """\/""", """/""")
  }

  def articleTitle: String = {
    link.data.title
  }

  def articleSiteName: String = {
    link.data.domain
  }

  def posterName: String = {
    link.data.author
  }

  def posterLink: String = {
    """https://www.reddit.com/user/""" + link.data.author
  }

  def subredditName: String = {
    """/r/""" + link.data.subreddit
  }

  def subredditLink: String = {
    """https://www.reddit.com/r/""" + link.data.subreddit
  }

  def commentCount: Int = {
    link.data.num_comments
  }

  def commentLink: String = {
    """https://www.reddit.com""" + link.data.permalink.replace( """\/""", """/""")
  }

  def asHtml: String = {
    var row = ""
    if (hidden) {
      row = """<tr style="display: none;"><td>"""
    }
    else {
      row = """<tr style=""><td>"""
    }
    row + {
      """<div class="col-md-12">""" + {
        """<div id="sub_row" class="row">""" + {
          """<div class="col-sm-12"  style="font-size: 16px; vertical-align: middle;">""" + {
            """<a id="link_seen_button" href="javascript:client.TableRow().refreshRow(""" + rowNumber + """)">""" + {
              """&nbsp;<span class="glyphicon glyphicon-remove-sign" style="color: orange;"></span>&nbsp;"""
            } +
              """</a>""" + {
              """<a id="the_link" href="""" + this.articleLink + """">""" + this.articleTitle + """</a>""" + """&nbsp;<a href=http://"""" + this.articleSiteName + """" style="font-size: 12px;">[""" + this.articleSiteName + """]</a>"""
            }
          } +
            """</div>"""
        } +
          """</div>"""
      } +
        """</div>""" +
        """<div class="row">""" + {
        """<div class="col-sm-12 col-xs-offset-1">""" + {
          """by """ + {
            """<a href="""" + this.posterLink + """"">""" + this.posterName + """</a>"""
          } +
            """@""" + {
            """<a href="""" + this.subredditLink + """"">""" + this.subredditName + """</a>"""
          } +
            """|""" + {
            """<a href="""" + this.commentLink + """">""" + this.commentCount.toString + """ comments</a>"""
          }
        } +
          """</div>"""
      } +
        """</div>"""
    } +
      """</td></tr>"""
  }
}

/*
 * Immediately download links from reddit
 * Do ajax call to server whenever links are exited out of to tell server we've seen that link.
 */
@JSExport
object TableRow {
  //TODO Keep a link source object that allows us to do the buffered checks and only pull out one at a time?

  //dom object for table body
  private val domTable = dom.document.getElementById("linkTableBody")

  //current links in table
  private var links = ArrayBuffer[Link]()

  private var after: js.Dynamic = null

  //private var linkQueue =  new mutable.Queue[Link]()

  def initialize(): Unit = {
    initialLoad(25)
  }

  def filterReposts(): Unit = {
    //seenCache.contains(link.data.url)
    val url = "/getLinks"
    val json = new StringBuilder()
    for (link <- links) {
      json.append(serializeLink(link) + "->")
    }
    json.replace(json.length - 2, json.length, "");
    Ajax.post(url, json.toString()).onSuccess {
      case xhr =>
        if (xhr.status == 200) {
          js.JSON.parse(xhr.responseText) match {
            case json: js.Array[js.Dynamic] => {
              var j = 0
              var k = 0
              val bools = Array.fill[Boolean](json.length)(true)
              for (result <- json) {
                if (result.asInstanceOf[Boolean]) {
                  println(links(j).data.title + " is a repost")
                  bools(j) = false
                  k += 1
                }
                j += 1
              }
              if(j-k == 1){
                domTable.innerHTML = ""
                initialLoad(24)
              }
              else {
                renderLinks(bools)
              }
            }
            case _ => println("Non-singular JSON object found! " + xhr.responseType)
          }
        }
      case _ => println("Failure to load links for user")
    }
  }

  //scala.js JSON is broken, taking drastic measures
  def serializeLink(link: Link): String = {
    var serString = link.data.toString()
    serString = link.data.url + ", " +
      serString.substring(9 + link.data.url.length(), 9 + link.data.url.length() + link.data.title.length())
        .replace(",", "") +
      serString.substring(9 + link.data.url.length() + link.data.title.length(), serString.length() - 1)
    return serString
  }

  def renderLinks(bools: Array[Boolean]): Unit = {
    var j = 0
    for (link <- links) {
      if (bools(j)) {
        domTable.innerHTML = domTable.innerHTML + new TableRow(link, j).asHtml
      }
      else {
        domTable.innerHTML = domTable.innerHTML + new TableRow(link, j,true).asHtml
      }
      j += 1
    }
  }

  def setLinks(links: ArrayBuffer[Link]) {
    this.links.appendAll(links)
  }

  def initialLoad(n: Int): Unit = {
    val url = "http://www.reddit.com/r/all.json?limit=" + n + "&after=" + after
    Ajax.get(url).onSuccess {
      case xhr =>
        if (xhr.status == 200) {
          js.JSON.parse(xhr.responseText) match {
            case json: js.Dynamic =>
              after = json.data.after
              setLinks(parseLinks(json))
              filterReposts()
            case _ => println("Non-singular JSON object found! " + xhr.responseType)
          }
        }
        else {
          println(xhr.statusText)
        }
      case _ => println("Non-JSON object found!")
    }
  }

  def loadNewLink(n: Int): Unit = {
    println("before after " + after)
    val url = "http://www.reddit.com/r/all.json?limit=" + 1 + "&after=" + after
    after = null
    Ajax.get(url).onSuccess {
      case xhr =>
        if (xhr.status == 200) {
          val json = js.JSON.parse(xhr.responseText)
          after = json.data.after
          println("after after " + after)
          val link = parseLink(json)
          println("pre-hello!" + link.data.title)
          Ajax.post("/getLink", serializeLink(link)).onSuccess {
            case xhr =>
              val flag = js.JSON.parse(xhr.responseText).asInstanceOf[Boolean]
              if (!flag) {
                domTable.children.item(n).setAttribute("style","display: none;") // = new TableRow(link,n,true).asHtml
                links.append(link)
                domTable.innerHTML = domTable.innerHTML + new TableRow(link, links.length-1).asHtml
                //val addUrl = "/addLink"
                //println(serializeLink(link))
                //Ajax.post(addUrl, serializeLink(link))
              }
              else {
                println("refreshed link was also found in database")
                loadNewLink(n)
              }
          }
        }
        else {
          println(xhr.statusText)
        }
      case _ => println("JSON not found for url load request")
    }
  }

  def parseLink(json: js.Dynamic): Link = {
    parseLinks(json)(0)
  }

  def parseLinks(json: js.Dynamic): ArrayBuffer[Link] = {
    val dataObj = json.data
    val links = dataObj.children.asInstanceOf[js.Array[js.Dynamic]]
    var retLinks = new ArrayBuffer[Link]
    for (link <- links) {
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
  def refreshRow(dyn: js.Dynamic): Unit = {
    val n = dyn.asInstanceOf[Int]
    if (n < 0 || n >= links.size) {
      println("n too big!")
    } else {
      val link = links(n)
      //seenCache.put(link.data.url,link)
      val url = "/addLink"
      Ajax.post(url, serializeLink(link)) //quote-unquote comma-separated, serialized string
      loadNewLink(n)
    }
  }


}

//TODO: Will we keep a "seen" array on local storage? Think more about this, probably need to build an object for it, or modify the TableRow companion object.
