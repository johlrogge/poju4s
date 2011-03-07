import sbt._

class Poju4s(info:ProjectInfo) extends DefaultProject(info) {
  val junitInterface = "com.novocode" % "junit-interface" % "0.5" % "test->default"
  val junit = "junit" % "junit" % "4.8.+" % "runtimeb"

  override def testOptions = ExcludeTests("poju4s.example.ExampleSpec" :: Nil) :: super.testOptions.toList
}
