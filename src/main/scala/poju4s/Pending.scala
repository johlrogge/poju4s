package poju4s
import exception._
trait Pending {
  val log: Log
  def pending(reason: String)(spec: => Unit) {
    (try {
      spec
      None
    } catch {
      case e: Throwable => Some(reason)
    }) map (r => {
      log.pending(r)
      throw new PendingException(r)
    }) orElse (throw new FixedButPendingException(reason))
  }
}
