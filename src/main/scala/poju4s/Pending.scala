package poju4s

trait Pending {
  val log: Log
  def pending(reason: String)(spec: => Unit) {
    (try {
      spec
      None
    } catch {
      case e: Throwable => Some("pending: " + reason)
    }) map (log.warn(_)) orElse(throw new FixedButPendingException(reason))
  }
}
