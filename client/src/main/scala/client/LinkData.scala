package client

import shared.LinkDataLike

case class LinkData(url:String,  title:String,  domain:String,  author:String,  subreddit:String,  num_comments:Int,  permalink:String) extends LinkDataLike{
  
}