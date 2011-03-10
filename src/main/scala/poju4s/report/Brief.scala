package poju4s.report

import poju4s.result._
import java.util.concurrent._

object Brief {

  def apply(toRun: List[Callable[Result]]) = {
    def println(str: String) = System.out.println(str);System.out.flush
    def print(str: String) = System.out.print(str);System.out.flush

    val results = toRun.map(x => {
      x.call match {
        case s: Success => print("."); s
        case i: Ignored => print("I"); i
        case x: Fixed => print("X"); x
        case p: Pending => print("P"); p
        case f: Failure => print("F"); f
        case e: Error => print("E"); e
        case r => print("?"); r
      }
    })

    val (tRun, tErr, tFail, tPend, tFixed, tIgn) = ((0, 0, 0, 0, 0, 0) /: results)((acc, curr) => {
      curr match {
        case s: Success => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6)
        case i: Ignored => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6 + 1)
        case x: Fixed   => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5 + 1, acc._6)
        case p: Pending => (acc._1 + 1, acc._2, acc._3, acc._4 + 1, acc._5, acc._6)
        case f: Failure => (acc._1 + 1, acc._2, acc._3 + 1, acc._4, acc._5, acc._6)
        case e: Error   => (acc._1 + 1, acc._2 + 1, acc._3, acc._4, acc._5, acc._6)
        case _ => (acc._1 + 1, acc._2, acc._3, acc._4, acc._5, acc._6)
      }
    })

    println("")
    println("Ran: " + tRun + ", Errors: " + tErr + ", Failures: " + tFail + ", Pending: " + tPend +", Fixed: " + tFixed+ ", Ignored: " + tIgn)
  }
}
