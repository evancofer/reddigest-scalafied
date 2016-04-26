package shared


case class User(name: String, password:String){
  def === (user:User):Boolean = {
     this.name === user.name && this.password === user.password
  }
}