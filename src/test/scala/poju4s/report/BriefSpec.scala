package poju4s.report

import org.junit._
import org.junit.Assert._
import poju4s._

object ReportFixture {
  import poju4s.util.StdXUtil.overrideStdOut
  def withExample(body: (Interaction, => String) => Unit) = {
    import poju4s.example.ExampleSpec
    overrideStdOut { output =>
      body(new ExampleSpec, output)
    }
  }
}

class BriefSpec {
  import ReportFixture._

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
    assertEquals(summary("X", 1, fixed = 1), output)
  }

  def summary(output: String, ran: Int, errors: Int = 0, failures: Int = 0, pending: Int = 0, fixed: Int = 0, ignored: Int = 0) = {
    val nl = System.getProperty("line.separator")
    output + nl +
      "Ran: " + ran + ", Errors: " + errors + ", Failures: " + failures + ", Pending: " + pending + ", Fixed: " + fixed + ", Ignored: " + ignored + nl
  }
}

class BriefSpecWithColor extends Colorable with Color {
  import ReportFixture._
  def brief = new Brief with Color

  @Test
  def marksSuccessWithDot = withExample { (spec, output) =>
    brief (spec.run('passingSpec))

    assertEquals(summary(success("."), 1), output)
  }

  @Test
  def markFailureWithF = withExample { (spec, output) =>
    brief(spec.run('failingSpec))
    assertEquals(summary(failure("F"), 1, failures = 1), output)
  }

  @Test
  def markErrorWithE = withExample { (spec, output) =>
    brief(spec.run('errorSpec))
    assertEquals(summary(error("E"), 1, errors = 1), output)
  }

  @Test
  def markPendingWithP = withExample { (spec, output) =>
    brief(spec.run('pendingSpec))
    assertEquals(summary(pending("P"), 1, pending = 1), output)
  }

  @Test
  def markIgnoredWithI = withExample { (spec, output) =>
    brief(spec.run('ignoredSpec))
    assertEquals(summary(ignored("I"), 1, ignored = 1), output)
  }

  @Test
  def markIgnoredFixedWithX = withExample { (spec, output) =>
    brief(spec.run('fixedSpec))
    assertEquals(summary(fixed("X"), 1, fixed = 1), output)
  }

  def summary(output: String, ran: Int, errors: Int = 0, failures: Int = 0, pending: Int = 0, fixed: Int = 0, ignored: Int = 0) = {
    val nl = System.getProperty("line.separator")
    output + nl +
      "Ran: " + ran + ", Errors: " + errors + ", Failures: " + failures + ", Pending: " + pending + ", Fixed: " + fixed + ", Ignored: " + ignored + nl
  }
}
