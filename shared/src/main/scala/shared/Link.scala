package shared

import play.api.libs.json._
//TODO: Create a TableLink in client side thats constructed from a link and does string formatting and can easily be inserted into the users link table.
case class Link(articleLink:String, articleTitle:String, articleSiteName:String, posterName:String, subName:String, commentCount:Int, commentLink:String){
/* Note(Evan): String editing such as:
 *    articleLink.replace("""\/""", """/""")
 * 		commentLink.replace("""\/""", """/""")
 * Should be done entirely client side in javascript.
 */
  /*
   * Note(Evan): Keep in mind that links to the posters
   * name and to the subreddit can also be generated client
   * side. Just preppend reddit user url to the posterName and
   * reddit sub url to sub name. Also, preppend subreddit
   * domain to permalink.
   */
}

implicit val linkWrites = new Writes[Link] {
  /*
   * (Should) Allow us to simply do Json.toJson(aLink) to generate
   * json responses for links.
   */
  def writes(link: Link) = Json.obj(
      "data"-> Json.obj(
          "url" -> link.articleLink,
          "title" -> link.articleTitle,
          "domain" -> link.articleSiteName,
          "author" -> link.posterName,
          "subreddit" -> link.subName,
          "num_comments" -> link.commentCount,
          "permalink" -> link.commentLink
      )
  )
}