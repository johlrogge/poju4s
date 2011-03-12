package object poju4s {
  type BriefReport = poju4s.report.Brief
  type Color = poju4s.report.Color
  val all = context.Global
  val brief = new BriefReport with Color
  
  implicit def i2res(i:Interaction):List[java.util.concurrent.Callable[poju4s.result.Result]] = {
    i.select()
  }
}
