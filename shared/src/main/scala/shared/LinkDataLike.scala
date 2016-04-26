package shared

trait LinkDataLike {
  def url:String 
  def title:String
  def domain:String
  def author:String
  def subreddit:String
  def num_comments:Int
  def permalink:String
}