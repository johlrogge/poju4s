package poju4s.report
import org.junit._
import org.junit.Assert._
import poju4s.result._
import poju4s.util._

class SummarySpec extends TextGoodies {
  @Test
  def toStringPrintsSummary {
    val sum = Summary(Success("cl", 't1) ::
      Error("cl", 't2, new Exception("error")) ::
      Failure("cl", 't3, new Exception("failure")) ::
      Pending("cl", 't4) ::
      Fixed("cl", 't5) ::
      Ignored("cl", 't6) ::
      Nil)

    assertEquals(
      "Ran: 6, Errors: 1, Failures: 1, Pending: 1, Fixed: 1, Ignored: 1, Succeeded: 1",
      sum.toString)

  }
}
