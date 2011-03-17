package poju4s

import org.junit._
import org.junit.Assert._
import poju4s.{ result => r }
import poju4s.example._

class InteractionSpec extends Pending with StdOutLog {
  def with_example(body: Interaction => Unit) = body(new ExampleSpec)
  def test(s: Symbol) = (classOf[ExampleSpec].getName, s)
  val FAILURE = new Exception("Failure")
  val ERROR = new Exception("Error")

  @Test
  def lists_all_specs_in_interactive_spec = with_example { spec =>
    val results = spec.list
    assertEquals(
      (test('passingSpec) ::
      test('failingSpec) ::
      test('errorSpec) ::
      test('pendingSpec) ::
      test('fixedSpec) ::
      test('ignoredSpec) ::
      Nil).toSet,
      results.toSet)
  }

  @Test
  def runs_all_specs_in_interacive_spec = with_example { spec =>
    val results = spec.select().map(_())
    assertEquals(
      (r.Success("poju4s.example.ExampleSpec", 'passingSpec) ::
      r.Failure("poju4s.example.ExampleSpec", 'failingSpec, FAILURE) ::
      r.Error("poju4s.example.ExampleSpec", 'errorSpec, ERROR) ::
      r.Pending("poju4s.example.ExampleSpec", 'pendingSpec) ::
      r.Fixed("poju4s.example.ExampleSpec", 'fixedSpec) ::
      r.Ignored("poju4s.example.ExampleSpec", 'ignoredSpec) ::
      Nil).toSet,
      results.toSet)
  }

  @Test
  def runs_one_selected_spec = with_example { spec =>
    val results = spec.select('failingSpec).map(_())
    assertEquals(
      r.Failure("poju4s.example.ExampleSpec", 'failingSpec, FAILURE)::
      Nil,
      results)
  }
}
