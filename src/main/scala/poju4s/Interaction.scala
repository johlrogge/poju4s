package poju4s

import poju4s.{ result => r }
import org.junit.runner._
import org.junit.runner.notification._
import scala.collection.JavaConversions._

object MonadicRunner {
  val TestAndClass = """([^(]+)\(([^)]+)\)""".r
  implicit def requestToOption(r: Request) = Some(r)
  implicit def runnerToOption(r: Runner) = Some(r)
  implicit def decriptionToOption(r: Description) = Some(r)
  def resolveFailure(f: Failure) = {
    val TestAndClass(t, g) = f.getDescription.getDisplayName
    f.getException match {
      case f: FixedButPendingException => r.Fixed(g, Symbol(t))
      case f: AssertionError => r.Failure(g, Symbol(t))
      case e => r.Error(g, Symbol(t))
    }
  }
}

trait Interaction {
  import MonadicRunner._

  def run: List[r.Result] = {
    var rs = List[r.Result]()
    for (
      (g, t) <- list;
      req <- Request.aClass(this.getClass);
      filtered <- req.filterWith(Description.createTestDescription(Class.forName(g), t.name))
    ) {
      val juc = new JUnitCore
      juc.addListener(new RunListener() {
        var failed: Option[Failure] = None
        override def testIgnored(d: Description) = { rs = r.Ignored(g, t) :: rs }
        override def testFailure(f: Failure) = { failed = Some(f) }
        override def testFinished(d: Description) = {
          val res = failed.map(resolveFailure) getOrElse (r.Success(g, t))
          rs = res :: rs
        }
      })
      val res = juc.run(filtered)
    }
    rs.reverse.foreach(println)
    rs.reverse
  }
  def list: List[(String, Symbol)] = {
    for (
      req <- Request.aClass(this.getClass).toList;
      run <- req.getRunner.toList;
      root <- run.getDescription.toList;
      chd <- root.getChildren.toList;
      TestAndClass(t, c) <- Some(chd.getDisplayName)
    ) yield { (c, Symbol(t)) }
  }
}

