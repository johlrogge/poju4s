package poju4s.result

case class Summary(result:List[Result]) {
  override def toString = {
    val (tRun, tErr, tFail, tPend, tFixed, tIgn, tSuc) = ((0, 0, 0, 0, 0, 0, 0) /: result)((acc, curr) => {
      curr match {
        case s: Success => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6, acc._7 + 1)
        case i: Ignored => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6 + 1, acc._7)
        case x: Fixed   => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5 + 1, acc._6, acc._7)
        case p: Pending => (acc._1 + 1, acc._2, acc._3, acc._4 + 1, acc._5, acc._6, acc._7)
        case f: Failure => (acc._1 + 1, acc._2, acc._3 + 1, acc._4, acc._5, acc._6, acc._7)
        case e: Error   => (acc._1 + 1, acc._2 + 1, acc._3, acc._4, acc._5, acc._6, acc._7)
        case _ => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6, acc._7)
      }
    })

    "Ran: " + tRun + ", Errors: " + tErr + ", Failures: " + tFail + ", Pending: " + tPend +", Fixed: " + tFixed+ ", Ignored: " + tIgn + ", Succeeded: "+ tSuc
  }
}
