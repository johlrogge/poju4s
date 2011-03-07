package poju4s

trait StdOutLog {
  val log:Log = new Log {
    def pending (message: => String) = System.out.println("PENDING: " + message)
  }
}
