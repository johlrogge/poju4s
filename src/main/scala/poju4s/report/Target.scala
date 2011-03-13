package poju4s.report

trait Target {
  def println(str:String)
}

import poju4s.util.TextGoodies
class StringTarget extends Target with TextGoodies {
  import scala.collection.mutable.StringBuilder
  val sb = new StringBuilder
  override def toString = sb.toString

  def println(str:String) = sb.append(str).append(nl)
}
