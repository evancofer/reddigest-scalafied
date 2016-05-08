package client

import shared.LinkDataLike
import shared.LinkLike

case class Link(userName:String, data:LinkDataLike) extends LinkLike{
    def this(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) = {
      this(userName, LinkData(url, title, domain, author, subreddit, num_comments, permalink))
    }
}