package poju4s

trait StdOutLog {
  val log:Log = new Log {
    def pending (message: => String) = System.out.println("PENDING: " + message)
    def ignored (message: => String) = System.out.println("IGNORED: " + message)
    def fixed (message: => String) = System.out.println("FIXED: " + message)
    def error (message: => String) = System.out.println("ERROR: " + message)
    def failure (message: => String) = System.out.println("FAILURE: " + message)
  }
}
