package poju4s.report

trait Color extends Styled {
  override val style = new Style {
    override val emphasis = (s: String) => "\033[1;37m"+s+"\033[m"
    override val deemphasis = (s: String) => "\033[1;30m"+s+"\033[m"
    override val line = (l: String) => "\033[1;33m"+l+"\033[m"
    override val file = (f: String) => "\033[1;36m"+f+"\033[m"
    override val method = (m: String) => emphasis(m)
    override val className = (c: String) => deemphasis(c)
    override val success = (s: String) => "\033[1;32m"+s+"\033[m"
    override val ignored = (i: String) => "\033[1;36m"+i+"\033[m"
    override val pending = (p: String) => "\033[2;33m"+p+"\033[m"
    override val fixed = (x: String) => "\033[1;4;33m"+x+"\033[m"
    override val failure = (f: String) => "\033[1;31m"+f+"\033[m"
    override val error = (e:  String) => "\033[2;31m"+e+"\033[m"
  }
}

trait TestStyle extends Styled {
  override val style = new Style {
    override val emphasis = (s: String) => "/"+s+"/"
    override val success = (s: String) => "s("+s+")"
    override val ignored = (i: String) => "i("+i+")"
    override val pending = (p: String) => "p("+p+")"
    override val fixed = (x: String) => "x("+x+")"
    override val failure = (f: String) => "f("+f+")"
    override val error = (e: String) => "e("+e+")"
  }
}

trait StyleSelector[T] {
  import poju4s.result._

  val success:T
  val ignored:T
  val pending:T
  val fixed:T
  val failure:T
  val error:T
  def apply (r:Result):T = r match {
    case _:Success => success
    case _:Ignored => ignored
    case _:Pending => pending
    case _:Fixed => fixed
    case _:Failure => failure
    case _:Error => error
  }
}


class Icon(s:Style) extends StyleSelector[String] { 
   lazy val success = s.success(".")
   lazy val ignored = s.ignored("I")
   lazy val pending = s.pending("p")
   lazy val fixed = s.fixed("X")
   lazy val failure = s.failure("F")
   lazy val error = s.error("E")
}

trait Style extends StyleSelector[(String) => String]{
  s => 
  import poju4s.result._

  val line = (l: String) => l
  val className = (c: String) => c
  val method = (m: String) => m
  val file = (f: String) => f
  val emphasis = (s: String) => s
  val deemphasis = (su: String) => su
  val success = (su: String) => su
  val ignored = (i: String) => i
  val pending = (p: String) => p
  val fixed = (x: String) => x
  val failure = (f: String) => f
  val error = (e: String) => e
  val icon:Icon = new Icon(this)
}

trait Styled {
  val style = new Style {}
}
