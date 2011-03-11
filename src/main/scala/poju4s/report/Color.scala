package poju4s.report

trait Color extends Colorable {
  override def success(s: => String):String = "\033[1;32m"+s+"\033[m"
  override def ignored(i: => String):String = "\033[1;36m"+i+"\033[m"
  override def pending(p: => String):String = "\033[2;33m"+p+"\033[m"
  override def fixed(x: => String):String = "\033[1;4;33m"+x+"\033[m"
  override def failure(f: => String):String = "\033[1;31m"+f+"\033[m"
  override def error(e: => String):String = "\033[2;31m"+e+"\033[m"
}

trait Colorable {
  def success(s: => String):String = s
  def ignored(i: => String):String = i
  def pending(p: => String):String = p
  def fixed(x: => String):String = x
  def failure(f: => String):String = f
  def error(e: => String):String = e
}
