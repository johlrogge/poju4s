package poju4s

import org.junit._
import org.junit.Assert._
import poju4s.{ result => r }
import poju4s.example._

class InteractionSpec extends Pending with StdOutLog {
  def with_example(body: Interaction => Unit) = body(new ExampleSpec)
  def test(s:Symbol) = (classOf[ExampleSpec].getName, s)

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
  def runs_all_specs_in_interacive_spec = with_example { spec =>
    val results = spec.run
    assertEquals(
      r.Success("poju4s.example.ExampleSpec", 'passingSpec) ::
      r.Failure("poju4s.example.ExampleSpec", 'failingSpec) ::
      r.Error("poju4s.example.ExampleSpec", 'errorSpec) ::
      r.Pending("poju4s.example.ExampleSpec", 'pendingSpec) ::
      r.Fixed("poju4s.example.ExampleSpec", 'fixedSpec) ::
      r.Ignored("poju4s.example.ExampleSpec", 'ignoredSpec) ::
      Nil,
      results)
  }
}
