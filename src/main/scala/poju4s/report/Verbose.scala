package poju4s.report

import poju4s.result._

object NoJUnit extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se: Option[StackTraceElement]) = {
    se.flatMap { x => { if (x.getClassName.contains(".junit.")) None else se } }
  }
}
object NoSBT extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se: Option[StackTraceElement]) = {
    se.flatMap { x => { if (x.getClassName.startsWith("sbt.") || x.getClassName.startsWith("xsbt.")) None else se } }
  }
}
object NoConsole extends (Option[StackTraceElement] => Option[StackTraceElement]) {
  def apply(se: Option[StackTraceElement]) = {
    se.flatMap { x =>
      {
        if (x.getFileName == "<console>" ||
          x.getClassName.startsWith("scala.tools.nsc.Interpreter")) None else se
      }
    }
  }
}

class Verbose(traceFilter: Option[StackTraceElement] => Option[StackTraceElement] = NoJUnit.compose(NoSBT).compose(NoConsole)) extends ReportElement {
  def print(summary: Summary, target: Target) = {
    val s = summary.style
    for (result <- summary) {
      result match {
        case ErrorDetail(group, spec, ed) => {
          val i = summary.style.icon
          target.println(i(result) + " " + spec.name + " (" + group + ")")
          def printException(ex: Throwable):Unit = {
            target.println("  - " + s.emphasis(ex.getMessage))
            target.println("     " + s(result)(ex.getClass.getName))
            for (
              next <- ex.getStackTrace;
              se <- traceFilter(Some(next))
            ) {
              target.println("      at " + s.className(se.getClassName()) + "." +
                s.method(se.getMethodName) + "(" +
                s.file(se.getFileName) + ":" +
                s.line("" + se.getLineNumber) + ")")
            }
            Option(ex.getCause).foreach{x =>
              target.println("    " + s.emphasis("caused by:"))
              printException(ex.getCause)
            }
          }
          printException(ed.error)
        }
        case _ => ()
      }
    }
    target.println("")
    summary
  }
}
