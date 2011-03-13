package poju4s.report

import org.junit._
import org.junit.Assert._
import poju4s.result._
import poju4s.util.TextGoodies

class ByStatusSummarySpec extends TextGoodies {

  def allStatusesTwoClasses = new Summary(Success("c1", 'success)::
      Ignored("c1", 'ignored)::
      Failure("c1", 'failure)::
      Error("c1", 'error)::
      Pending("c2", 'pending)::
      Fixed("c2", 'fixed)::
      Failure("c2", 'failure2)::
      Nil) with TestStyle

  def fixture(summary: => Summary)(body: (Summary, Target) => Unit) =  body(summary, new StringTarget)

  @Test
  def groupsTestsByClass = fixture(allStatusesTwoClasses) { (summary, target) =>
    summary >>: ByStatusSummary() >>: target
    val s = summary.style

    val expected = s.error("c1")+nl+
    "  " + s.ignored("I") + " ignored" + nl +
    "  " + s.failure("F") + " failure" + nl +
    "  " + s.error(  "E") + " error" + nl + nl+
    s.failure("c2")+ nl +
    "  " + s.pending("P") + " pending" + nl +
    "  " + s.fixed("X") + " fixed" + nl +
    "  " + s.failure("F") + " failure2" + nl + nl

    assertEquals(expected, target.toString)
  }
}
