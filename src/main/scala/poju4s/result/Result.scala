package poju4s.result


sealed abstract class Result(spec:Symbol)
case class Success(spec:Symbol) extends Result(spec)
case class Ignored(spec:Symbol) extends Result(spec)
case class Pending(spec:Symbol) extends Result(spec)
case class Fixed(spec:Symbol) extends Result(spec)
case class Failure(spec:Symbol) extends Result(spec)
case class Error(spec:Symbol) extends Result(spec)
