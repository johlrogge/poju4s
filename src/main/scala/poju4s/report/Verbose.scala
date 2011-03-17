package poju4s.report

import poju4s.result._

object NoJUnit extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se:Option[StackTraceElement]) = {
    se.flatMap{x => {if(x.getClassName.contains(".junit.")) None else se}}
  }
}
object NoSBT extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se:Option[StackTraceElement]) = {
    se.flatMap{x => {if(x.getClassName.startsWith("sbt.") || x.getClassName.startsWith("xsbt.")) None else se}}
  }
}
object NoConsole extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se:Option[StackTraceElement]) = {
    se.flatMap{x => {if (x.getFileName == "<console>" ||
                         x.getClassName.startsWith("scala.tools.nsc.Interpreter")) None else se}}
  }
}


class Verbose(traceFilter: Option[StackTraceElement] => Option[StackTraceElement] = NoJUnit.compose(NoSBT).compose(NoConsole)) extends ReportElement {
  def print(summary: Summary, target: Target) = {
    val s = summary.style
    for (result <- summary) {
      result match {
        case ErrorDetail(group, spec, ed) => {
          val i = summary.style.icon
          target.println(i.apply(result) + " " + spec.name + " (" + group + ")")
          target.println("  - " + s.emphasis(ed.error.getMessage))
          target.println("     " + s(result)(ed.error.getClass.getName))
          for(next <- ed.error.getStackTrace;
              se <- traceFilter(Some(next))) {
            target.println("      at " + s.className(se.getClassName()) + "." + 
                                         s.method(se.getMethodName)+"("+ 
                                         s.file(se.getFileName)+":"+
                                         s.line(""+se.getLineNumber)+")")
          }
        }
        case _ => ()
      }
    }
    target.println("")
    summary
  }
}
