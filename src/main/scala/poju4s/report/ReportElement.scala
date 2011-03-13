package poju4s.report


trait ReportElement {
  def print(summary:Summary, target:Target):Summary
  def >>:(summary:Summary) = summary >>: this >>: new SystemOutTarget
  def >>:(element:ReportElement) = element >>: this >>: new SystemOutTarget
}
