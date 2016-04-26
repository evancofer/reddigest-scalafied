package client

import shared.UserLike

case class User (val name:String, val password:String) extends UserLike {
  //TODO figure out what this will do on the client side, and also work on defining a user registration type.
}



