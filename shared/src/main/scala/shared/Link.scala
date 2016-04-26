package shared

case class Link(val userName:String, val data:LinkData){ 
    def this(userName:String, url:String, title:String, domain:String, author:String, subreddit:String, num_comments:Int, permalink:String) = this(userName, LinkData(url, title, domain, author, subreddit, num_comments, permalink))
}

case class LinkData(val url:String, val title:String, val domain:String, val author:String, val subreddit:String, val num_comments:Int, val permalink:String){
}




