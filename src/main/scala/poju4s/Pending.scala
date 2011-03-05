package poju4s

trait Pending {
  val log: Log
  def pending(reason: String)(spec: => Unit) {
    (try {
      spec
      None
    } catch {
      case e: Throwable => Some(reason)
    }) map (log.pending(_)) orElse(throw new FixedButPendingException(reason))
  }
}
