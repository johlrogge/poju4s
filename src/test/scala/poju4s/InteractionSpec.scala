package poju4s

import org.junit._
import org.junit.Assert._
import poju4s.{result => r}

class InteractionSpec extends Pending with StdOutLog {
  class ExampleSpec extends Interaction with Pending with TestLog {
    @Test
    def passingSpec = ()
    @Test
    def failingSpec = fail("supposed to be failing")
    @Test
    def errorSpec = error("an error spec")
    @Test
    def pendingSpec = pending("supposed to be pending") { fail("ignored failure") }
    @Test
    def fixedSpec = pending("supposed to be fixed") {}
    @Ignore("reason")
    @Test
    def ignoredSpec = fail("fails if not ignored")
  }

  def with_example(body: ExampleSpec => Unit) = body(new ExampleSpec)

  @Test
  def runs_all_specs_in_interacive_spec = pending("need to run tests via JUnit")( with_example{ spec =>
    val results = spec.run
    assertEquals(
      r.Success('passingSpec) ::
      r.Failure('failingSpec) ::
      r.Error('errorSpec) ::
      r.Pending('pendingSpec) ::
      r.Fixed('fixedSpec) ::
      r.Ignored('ignoredSpec) ::
      Nil,
      results)
  })
}
