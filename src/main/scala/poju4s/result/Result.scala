package poju4s.result

trait ResultUnapplication {
  def unapply(result:(Result)) = Some((result.group, result.spec))
}

trait ErrorDetail {
  val error:Throwable
}

object ErrorDetail {
  def unapply(result:Result):Option[(String, Symbol, ErrorDetail)] = {
    result match {
      case ed:ErrorDetail => Some((result.group, result.spec, ed))
      case _ =>  None
    }
  }
}

object Result extends ResultUnapplication {
  def numeric(r: Result) = {
    r match {
      case s: Success => 0
      case i: Ignored => 1
      case x: Fixed => 2
      case p: Pending => 3
      case f: Failure => 4
      case e: Error => 5
      case _ => 6
    }
  }
  val BySeverity = new Ordering[Result] {
    def compare(x: Result, y: Result) = numeric(x) - numeric(y)
  }
}


sealed abstract class Result(val group: String, val spec: Symbol) {
  override def hashCode = group.hashCode ^ spec.hashCode
  override def equals(other:Any) = other match {
    case or:Result => or.group == group && or.spec == spec && getClass.isAssignableFrom(or.getClass)
    case _ => false
  }
}

case class Success(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Ignored(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Pending(override val group: String, override val spec: Symbol) extends Result(group, spec)

case class Fixed(override val group: String, override val spec: Symbol) extends Result(group, spec)

object Failure extends ResultUnapplication {
  def apply(group:String, spec:Symbol, error:Throwable) = new Failure(group, spec, error)
}

class Failure(group: String, spec: Symbol, val error:Throwable) extends Result(group, spec) with ErrorDetail

object Error extends ResultUnapplication {
  def apply(group:String, spec:Symbol, error:Throwable) = new Error(group, spec, error)
}

class Error(group: String, spec: Symbol, val error:Throwable) extends Result(group, spec) with ErrorDetail
