package poju4s

trait Log {
  def pending(message: => String)
}
