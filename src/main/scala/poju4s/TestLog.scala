package poju4s

/**
 * Mixin this trait to get a list of messages logged in runtime
 */
trait TestLog {
  var messages:List[LogMessage] = Nil
  val log:Log = new Log {
    def warn(msg: => String) = messages = Warn(msg) :: messages
  }
}

sealed trait LogMessage
case class Warn(message:String) extends LogMessage
