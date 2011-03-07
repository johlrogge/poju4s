package poju4s.example

import org.junit._
import org.junit.Assert._
import poju4s._

class ExampleSpec extends Interaction with Pending with TestLog {
  @Test
  def passingSpec = ()
  @Test
  def failingSpec = fail("supposed to be failing")
  @Test
  def errorSpec { error("an error spec") }
  @Test
  def pendingSpec = pending("supposed to be pending") { fail("ignored failure") }
  @Test
  def fixedSpec = pending("supposed to be fixed") {}
  @Ignore("reason")
  @Test
  def ignoredSpec = fail("fails if not ignored")
}

