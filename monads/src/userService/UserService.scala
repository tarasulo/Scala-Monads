package userService

/**
 * Get the name of the best friend of the best friend
 * user doesn't have a best friend, please return "No bf"
 * if there is no user with entered name - please return "No user"
 */
object UserService extends App {
  case class User(name: String, bf: User)

  val users = List(
    User("Mike", User("Sam", User("Bob", User("Alice", null)))),
    User("John", User("Joe", null)),
    User("Empty", null),
  )

  object Service {
    def findUser(users: List[User], name: String): Option[User] = users.find(_.name == name)
  }

  def getBf(user: User): Option[User] = Option(user.bf)

  def bf(nameToFind: String) = Service.findUser(users, nameToFind) match {
    case None => "No user"
    case Some(user) => getBf(user).flatMap(getBf).map(_.name).getOrElse("No bf")
  }

  println(bf("Mike"))
  println(bf("John"))
  println(bf("Empty"))
  println(bf("Test"))
}
