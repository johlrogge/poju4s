package poju4s

import poju4s.{ result => r }
import org.junit.runner._
import scala.collection.JavaConversions._

object MonadicRunner {
  implicit def requestToOption(r: Request) = Some(r)
  implicit def runnerToOption(r: Runner) = Some(r)
  implicit def decriptionToOption(r: Description) = Some(r)
}

trait Interaction {
  def run: List[r.Result] = Nil
  def list: List[(Symbol, Symbol)] = {
    import MonadicRunner._
    val TestAndClass = """([^(]+)\(([^)]+)\)""".r
    for (
      req <- Request.aClass(this.getClass).toList;
      run <- req.getRunner.toList;
      root <- run.getDescription.toList;
      chd <- root.getChildren.toList;
      TestAndClass(t, c) <- Some(chd.getDisplayName)
    ) yield { (Symbol(c),Symbol(t)) }
  }
}
