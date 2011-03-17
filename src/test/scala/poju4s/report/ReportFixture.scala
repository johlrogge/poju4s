package poju4s.report

import poju4s.result._
import poju4s.Interaction

object ReportFixture {
  val FAILURE_EXCEPTION = new AssertionError("assertion problem")
  val ERROR_EXCEPTION = new RuntimeException("runtime problem")

  def allStatusesTwoClasses = new Summary(Success("c1", 'success) ::
    Ignored("c1", 'ignored) ::
    Failure("c1", 'failure, FAILURE_EXCEPTION) ::
    Error("c1", 'error, ERROR_EXCEPTION) ::
    Pending("c2", 'pending) ::
    Fixed("c2", 'fixed) ::
    Failure("c2", 'failure2, FAILURE_EXCEPTION) ::
    Nil) with TestStyle

  def fixture(summary: => Summary)(body: (Summary, Target) => Unit) = body(summary, new StringTarget)

  import poju4s.util.StdXUtil.overrideStdOut

  def withExample(body: (Interaction, => String) => Unit) = {
    import poju4s.example.ExampleSpec
    overrideStdOut { output =>
      body(new ExampleSpec, output)
    }
  }
  def summary(output: String, ran: Int, errors: Int = 0, failures: Int = 0, pending: Int = 0, fixed: Int = 0, ignored: Int = 0, succeeded: Int = 0) = {
    val nl = System.getProperty("line.separator")
    output + nl
  }
}

