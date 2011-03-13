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
      target.println(highestStyle(vs, s)(k))
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
      case s: Success => style.success _
      case i: Ignored => style.ignored _
      case p: Pending => style.pending _
      case x: Fixed => style.fixed _
      case f: Failure => style.failure _
      case e: Error => style.error _
      case _ => style.error _
    }).getOrElse(style.error _)
  }
}
