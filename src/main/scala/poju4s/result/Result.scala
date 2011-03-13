package poju4s.result

object Result {
  def unapply(result: Result) = Some((result.group, result.spec))
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

sealed abstract class Result(val group: String, val spec: Symbol)
case class Success(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Ignored(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Pending(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Fixed(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Failure(override val group: String, override val spec: Symbol) extends Result(group, spec)
case class Error(override val group: String, override val spec: Symbol) extends Result(group, spec)
