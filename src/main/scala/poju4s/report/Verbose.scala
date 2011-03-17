package poju4s
package report

import result._

object Filters {
  type TraceFilter = (String, Array[StackTraceElement]) => Array[StackTraceElement]
  type TraceElementFilter = Option[StackTraceElement] => Option[StackTraceElement]

  object NoJUnit extends TraceElementFilter {
    def apply(se: Option[StackTraceElement]) = {
      se.flatMap { x => { if (x.getClassName.contains(".junit.")) None else se } }
    }
  }
  object NoSBT extends TraceElementFilter {
    def apply(se: Option[StackTraceElement]) = {
      se.flatMap { x => { if (x.getClassName.startsWith("sbt.") || x.getClassName.startsWith("xsbt.")) None else se } }
    }
  }
  object NoConsole extends TraceElementFilter {
    def apply(se: Option[StackTraceElement]) = {
      se.flatMap { x =>
        {
          if (x.getFileName == "<console>" ||
            x.getClassName.startsWith("scala.tools.nsc.Interpreter")) None else se
        }
      }
    }
  }

  object WholeTrace extends TraceFilter {
    def apply(group: String, se: Array[StackTraceElement]) = {
      se
    }
  }
  object StopAtTestClass extends TraceFilter {
    def apply(group: String, stack: Array[StackTraceElement]) = {
      val shortened = stack.reverse.dropWhile(!_.getClassName.startsWith(group)).reverse
      if (shortened.isEmpty) stack
      else shortened
    }
  }
}

import Filters._

class Verbose(traceFilter: TraceFilter = StopAtTestClass, traceElementFilter: TraceElementFilter = NoJUnit.compose(NoSBT).compose(NoConsole)) extends ReportElement {
  def print(summary: Summary, target: Target) = {
    val s = summary.style
    for (result <- summary) {
      result match {
        case ErrorDetail(group, spec, ed) => {
          val i = summary.style.icon
          target.println(i(result) + " " + spec.name + " (" + group + ")")

          def printException(ex: Throwable): Unit = {
            target.println("  - " + s.emphasis(ex.getMessage))
            target.println("     " + s(result)(ex.getClass.getName))
            for (
              next <- traceFilter(group, ex.getStackTrace);
              se <- traceElementFilter(Some(next))
            ) {
              target.println("      at " + s.className(se.getClassName()) + "." +
                s.method(se.getMethodName) + "(" +
                s.file(se.getFileName) + ":" +
                s.line("" + se.getLineNumber) + ")")
            }
            Option(ex.getCause).foreach { x =>
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
