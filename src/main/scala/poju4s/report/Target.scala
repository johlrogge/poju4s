package poju4s.report

trait Target {
  def println(str:String)
  def >>:(element:ReportElement):ReportTarget = {
    val t = this
    val next = new ReportTarget {
      def target = t
      def >>:(summary:Summary):Summary = summary
    }
    element >>: next
  }
}

trait ReportTarget {
  def target:Target
  def >>:(otherElement:ReportElement):ReportTarget = {
    val next = this
    new ReportTarget {
      def >>:(summary:Summary):Summary = {
        otherElement.print(summary, target) >>: next
      }
      def target = next.target
    }
  }
  
  def >>:(summary:Summary):Summary
}

import poju4s.util.TextGoodies
class StringTarget extends Target with TextGoodies {
  import scala.collection.mutable.StringBuilder
  val sb = new StringBuilder
  override def toString = sb.toString

  def println(str:String) = sb.append(str).append(nl)
}

class SystemOutTarget extends Target {
  def println(str:String) = System.out.println(str)
}
