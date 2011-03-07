package poju4s

/**
 * Mixin this trait to get a list of messages logged in runtime
 */
trait TestLog {
  var messages:List[LogMessage] = Nil
  val log:Log = new Log {
    def pending(msg: => String) = messages = LogPending(msg) :: messages

  }
}

sealed trait LogMessage
case class LogPending(message:String) extends LogMessage


