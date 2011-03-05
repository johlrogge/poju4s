package poju4s

trait StdOutLog {
  val log:Log = new Log {
    def warn (message: => String) = System.out.println("WARN: " + message)
  }
}
