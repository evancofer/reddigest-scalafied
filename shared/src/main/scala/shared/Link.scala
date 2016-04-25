package shared


case class Link(val data:LinkData){
  
}
case class LinkData(val url:String, val title:String, val domain:String, val author:String, val subreddit:String, val num_comments:Int, val permalink:String){

}



