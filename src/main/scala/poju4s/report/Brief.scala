package poju4s.report

import poju4s.result._
import java.util.concurrent._

object Brief {
  def apply(toRun: List[Callable[Result]]) = new Brief().apply(toRun)
}

class Brief extends Colorable {

  def apply(toRun: List[Callable[Result]]) = {
    def println(str: String) = System.out.println(str);System.out.flush
    def print(str: String) = System.out.print(str);System.out.flush

    val results = toRun.map(x => {
      x.call match {
        case s: Success => print(success(".")); s
        case i: Ignored => print(ignored("I")); i
        case x: Fixed => print(fixed("X")); x
        case p: Pending => print(pending("P")); p
        case f: Failure => print(failure("F")); f
        case e: Error => print(error("E")); e
        case r => print("?"); r
      }
    })
    println("")
    Summary(results)
  }
}
