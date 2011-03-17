package object poju4s {
  type BriefReport = poju4s.report.Brief
  type Color = poju4s.report.Color
  val all = context.Global
  val brief = new BriefReport with Color
  val out = new report.SystemOutTarget
  val summary = report.ByStatusSummary()
  val verbose = new report.Verbose()
  implicit def i2res(i:Interaction):List[() => poju4s.result.Result] = {
    i.select()
  }
}
