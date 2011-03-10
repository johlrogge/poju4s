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
  def resolveAssumption(af:Failure) = {
    val TestAndClass(t, g) = af.getDescription.getDisplayName
    af.getException match {
      case p:PendingException => r.Pending(g, Symbol(t))
      case e => r.Ignored(g, Symbol(t))
    }
  }
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

  def run(specsToRun:Symbol*): List[r.Result] = {
    var rs = List[r.Result]()
    for (
      (g, t) <- specDescriptors(specsToRun.toList);
      req <- Request.aClass(this.getClass);
      thldr <- Some(Thread.currentThread.getContextClassLoader);
      filtered <- req.filterWith(Description.createTestDescription(thldr.loadClass(g), t.name))
    ) {
      val juc = new JUnitCore
      juc.addListener(new RunListener() {
        var failed: Option[Failure] = None
        var aFailed:Option[Failure] = None
        override def testAssumptionFailure(af:Failure) = {aFailed = Some(af)}
        override def testIgnored(d: Description) = { rs = r.Ignored(g, t) :: rs }
        override def testFailure(f: Failure) = { failed = Some(f) }
        override def testFinished(d: Description) = {
          val res = failed.map(resolveFailure) orElse aFailed.map(resolveAssumption) getOrElse (r.Success(g, t))
          rs = res :: rs
        }
      })
      val res = juc.run(filtered)
    }
    rs.reverse
  }
  
  def specDescriptors(specs:List[Symbol]) = specs match {
    case Nil => list
    case _ => specs map((getClass.getName, _))
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

