package poju4s
package report

import result._

object Filters {
  type TraceFilter = (String, Array[StackTraceElement]) => (Array[StackTraceElement])
  type TraceElementFilter = Option[StackTraceElement] => Option[StackTraceElement]
  trait ComposableTraceFilter extends TraceFilter {
    def compose(other:TraceFilter) = {
      (group:String, elements:Array[StackTraceElement]) => other(group, apply(group, elements))
    }
  }


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


  object WholeTrace extends ComposableTraceFilter {
    def apply(group: String, se: Array[StackTraceElement]) = {
      se
    }
  }
  object StopAtTestClass extends ComposableTraceFilter {
    def apply(group: String, stack: Array[StackTraceElement]) = {
      val shortened = stack.reverse.dropWhile(!_.getClassName.startsWith(group)).reverse
      if (shortened.isEmpty) stack
      else shortened
    }
  }

  def elements(filter:TraceElementFilter) = new ComposableTraceFilter {
    def apply(group: String, st: Array[StackTraceElement]) = {
      for(next <- st;
          e <- filter(Some(next))) 
      yield e
    }
  }
}

import Filters._

class Verbose(traceFilter: TraceFilter = StopAtTestClass.compose(elements(NoJUnit.compose(NoSBT).compose(NoConsole)))) extends ReportElement {
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
            for (se <- traceFilter(group, ex.getStackTrace)) {
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
