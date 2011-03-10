package poju4s.report

import org.junit._
import org.junit.Assert._
import poju4s._

class BriefSpec {
  import poju4s.util.StdXUtil.overrideStdOut

  def withExample(body: (Interaction, => String) => Unit) = {
    import poju4s.example.ExampleSpec
    overrideStdOut { output =>
      body(new ExampleSpec, output)
    }
  }

  @Test
  def marksSuccessWithDot = withExample { (spec, output) =>
    Brief(spec.run('passingSpec))

    assertEquals(summary(".", 1), output)
  }

  @Test
  def markFailureWithF = withExample { (spec, output) =>
    Brief(spec.run('failingSpec))
    assertEquals(summary("F", 1, failures = 1), output)
  }

  @Test
  def markErrorWithE = withExample { (spec, output) =>
    Brief(spec.run('errorSpec))
    assertEquals(summary("E", 1, errors = 1), output)
  }

  @Test
  def markPendingWithP = withExample { (spec, output) =>
    Brief(spec.run('pendingSpec))
    assertEquals(summary("P", 1, pending = 1), output)
  }

  @Test
  def markIgnoredWithI = withExample { (spec, output) =>
    Brief(spec.run('ignoredSpec))
    assertEquals(summary("I", 1, ignored = 1), output)
  }

  @Test
  def markIgnoredFixedWithX = withExample { (spec, output) =>
    Brief(spec.run('fixedSpec))
    assertEquals(summary("X",  1, fixed = 1), output)
  }

  def summary(output: String, ran: Int, errors: Int = 0, failures: Int = 0, pending: Int = 0, fixed:Int = 0, ignored: Int = 0) = {
    val nl = System.getProperty("line.separator")
    output + nl +
      "Ran: " + ran + ", Errors: " + errors + ", Failures: " + failures + ", Pending: " + pending + ", Fixed: "+ fixed + ", Ignored: " + ignored + nl
  }
}
