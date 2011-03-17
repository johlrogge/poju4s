package poju4s.report

import org.junit._
import org.junit.Assert._
import poju4s._
import poju4s.result._


class BriefSpec {
  import poju4s.report.ReportFixture._

  @Test
  def marksSuccessWithDot = withExample { (spec, output) =>
    Brief(spec.select('passingSpec))

    assertEquals(summary(".", 1, succeeded = 1), output)
  }

  @Test
  def markFailureWithF = withExample { (spec, output) =>
    Brief(spec.select('failingSpec))
    assertEquals(summary("F", 1, failures = 1), output)
  }

  @Test
  def markErrorWithE = withExample { (spec, output) =>
    Brief(spec.select('errorSpec))
    assertEquals(summary("E", 1, errors = 1), output)
  }

  @Test
  def markPendingWithP = withExample { (spec, output) =>
    Brief(spec.select('pendingSpec))
    assertEquals(summary("P", 1, pending = 1), output)
  }

  @Test
  def markIgnoredWithI = withExample { (spec, output) =>
    Brief(spec.select('ignoredSpec))
    assertEquals(summary("I", 1, ignored = 1), output)
  }

  @Test
  def markIgnoredFixedWithX = withExample { (spec, output) =>
    Brief(spec.select('fixedSpec))
    assertEquals(summary("X", 1, fixed = 1), output)
  }
}

class BriefSpecWithColor extends Styled with Color {
  import ReportFixture._
  val brief = new Brief with Color

  @Test
  def marksSuccessWithDot = withExample { (spec, output) =>
    brief(spec.select('passingSpec))

    assertEquals(summary(style.success("."), 1, succeeded = 1), output)
  }

  @Test
  def markFailureWithF = withExample { (spec, output) =>
    brief(spec.select('failingSpec))
    assertEquals(summary(style.failure("F"), 1, failures = 1), output)
  }

  @Test
  def markErrorWithE = withExample { (spec, output) =>
    brief(spec.select('errorSpec))
    assertEquals(summary(style.error("E"), 1, errors = 1), output)
  }

  @Test
  def markPendingWithP = withExample { (spec, output) =>
    brief(spec.select('pendingSpec))
    assertEquals(summary(style.pending("P"), 1, pending = 1), output)
  }

  @Test
  def markIgnoredWithI = withExample { (spec, output) =>
    brief(spec.select('ignoredSpec))
    assertEquals(summary(style.ignored("I"), 1, ignored = 1), output)
  }

  @Test
  def markIgnoredFixedWithX = withExample { (spec, output) =>
    brief(spec.select('fixedSpec))
    assertEquals(summary(style.fixed("X"), 1, fixed = 1), output)
  }

  @Test
  def returnsBrowsableSummary = withExample { (spec, output) =>
    val res = brief(spec.select('fixedSpec))
    assertEquals(Summary(Fixed("poju4s.example.ExampleSpec", 'fixedSpec) :: Nil), res)
  }

  @Test
  def returnedSummaryInheritsStyle = withExample {(spec, output) =>
    val res = brief(spec.select('fixedSpec))
    assertSame(brief.style, res.style)
  }
}
