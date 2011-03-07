package poju4s

import org.junit._
import org.junit.Assert._
import poju4s.{ result => r }
import poju4s.example._

class InteractionSpec extends Pending with StdOutLog {
  def with_example(body: Interaction => Unit) = body(new ExampleSpec)
  def test(s:Symbol) = (Symbol(classOf[ExampleSpec].getName), s)

  @Test
  def lists_all_specs_in_interactive_spec = with_example { spec =>
    val results = spec.list
    assertEquals(
      test('passingSpec) ::
      test('failingSpec) ::
      test('errorSpec) ::
      test('pendingSpec) ::
      test('fixedSpec) ::
      test('ignoredSpec) ::
      Nil,
      results)
  }

  @Test
  def runs_all_specs_in_interacive_spec = pending("need to run tests via JUnit")(with_example { spec =>
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
