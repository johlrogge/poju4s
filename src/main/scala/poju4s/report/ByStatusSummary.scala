package poju4s.report

import poju4s.result._

case class ByStatusSummary() extends ReportElement {

  def print(summary: Summary, target: Target) = {
    val groupedRes = summary.result.filter(Result.numeric(_) > 0).groupBy(_.group)

    val s = summary.style
    for (
      k <- groupedRes.keys.toList.sortWith((a, b) => a.compare(b) < 0);
      vs <- groupedRes.get(k)
    ) {
      val hs = highestStyle(vs, s)
      target.println(hs(k))
      for (r <- vs) {
        target.println("  " + icon(r, summary.style) + " " + r.spec.name)
      }
      target.println("")
    }
    summary
  }

  def icon(r: Result, style: Style) = r match {
    case s: Success => style.success("S")
    case i: Ignored => style.ignored("I")
    case p: Pending => style.pending("P")
    case x: Fixed => style.fixed("X")
    case f: Failure => style.failure("F")
    case e: Error => style.error("E")
    case _ => style.error("?")
  }

  def highestStyle(results: List[Result], style: Style) = {
    results.sorted(Result.BySeverity.reverse).headOption.map(x => x match {
      case s: Success => style.success
      case i: Ignored => style.ignored
      case p: Pending => style.pending
      case x: Fixed => style.fixed
      case f: Failure => style.failure
      case e: Error => style.error
      case _ => style.error
    }).getOrElse(style.error)
  }
}
