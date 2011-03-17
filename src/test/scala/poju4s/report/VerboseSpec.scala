package poju4s.report

import org.junit.Assert._
import org.junit._

class VerboseSpec {
import ReportFixture._


  @Test
  def printsErrorMessageForFailures = fixture(allStatusesTwoClasses) {
    (summary, target) =>
    summary >>: new Verbose() >>: target
    val s = summary.style
    assertTrue(target.toString, target.toString.contains(s.emphasis(FAILURE_EXCEPTION.getMessage)))
  }

  @Test
  def printsErrorMessageForErrors = fixture(allStatusesTwoClasses) {
    (summary, target) =>
    summary >>: new Verbose() >>: target
    assertTrue(target.toString,  target.toString.contains(ERROR_EXCEPTION.getMessage))
  }

  @Test
  def printsStackTraceForFailures = fixture(allStatusesTwoClasses) {
    (summary, target) =>
    summary >>: new Verbose() >>: target
    assertTrue(target.toString,  target.toString.contains(ERROR_EXCEPTION.getStackTrace()(0).toString))
  }

  @Test
  def printsExceptionName = fixture(allStatusesTwoClasses) {
    (summary, target) => 
    summary >>: new Verbose() >>: target
    val s = summary.style
    assertTrue(target.toString, target.toString.contains(s.failure(FAILURE_EXCEPTION.getClass.getName)))
  }

  @Test
  def filtersJUnit = fixture(allStatusesTwoClasses) {
    (summary, target) =>
    summary >>: new Verbose() >>: target
    assertFalse(target.toString,  target.toString.contains(".junit."))
  }

  

}
