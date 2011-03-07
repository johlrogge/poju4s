package poju4s.result


sealed abstract class Result(group:String, spec:Symbol)
case class Success(group:String, spec:Symbol) extends Result(group, spec)
case class Ignored(group:String, spec:Symbol) extends Result(group, spec)
case class Pending(group:String, spec:Symbol) extends Result(group, spec)
case class Fixed(group:String, spec:Symbol) extends Result(group, spec)
case class Failure(group:String, spec:Symbol) extends Result(group, spec)
case class Error(group:String, spec:Symbol) extends Result(group, spec)
