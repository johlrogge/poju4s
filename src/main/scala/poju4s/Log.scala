package poju4s

trait Log {
  def error(message: => String)
  def failure(message: => String)
  def ignored(message: => String)
  def pending(message: => String)
  def fixed(message: => String)
}
