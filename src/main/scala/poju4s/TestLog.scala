package poju4s

/**
 * Mixin this trait to get a list of messages logged in runtime
 */
trait TestLog {
  var messages:List[LogMessage] = Nil
  val log:Log = new Log {
    def pending(msg: => String) = messages = LogPending(msg) :: messages
    def fixed(msg: => String) = messages = LogFixed(msg) :: messages
    def ignored(msg: => String) = messages = LogIgnored(msg) :: messages
    def failure(msg: => String) = messages = LogFailure(msg) :: messages
    def error(msg: => String) = messages = LogError(msg) :: messages
  }
}

sealed trait LogMessage
case class LogPending(message:String) extends LogMessage
case class LogFailure(message:String) extends LogMessage
case class LogError(message:String) extends LogMessage
case class LogFixed(message:String) extends LogMessage
case class LogIgnored(message:String) extends LogMessage

