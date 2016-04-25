package shared

import play.api.libs.json._

case class Link(val data:LinkData)
case class LinkData(val url:String, val title:String, val domain:String, val author:String, val subreddit:String, val num_comments:Int, val permalink:String)

implicit val linkDataWrites = new Writes[LinkData] {
  def writes(data: LinkData) = Json.obj(
          "url" -> data.url,
          "title" -> data.title,
          "domain" -> data.domain,
          "author" -> data.author,
          "subreddit" -> data.subreddit,
          "num_comments" -> data.num_comments,
          "permalink" -> data.permalink
  )
}

implicit val linkWrites = new Writes[Link] {
  def writes(link: Link) = Json.obj(
      "data" -> Json.toJson(link.data)
  )
}