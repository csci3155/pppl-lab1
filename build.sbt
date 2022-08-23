name := "pppl-lab1"

lazy val commonSettings = Seq(
  organization := "edu.colorado.cs",
  version := "3.4.4",

  scalaVersion := "2.13.6",
  scalacOptions ++= Seq(
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-feature", // Emit warning for features that should be imported explicitly
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-explaintypes", // Explain type errors in more detail.
    "-Ywarn-extra-implicit", // more than one implicit parameter section is defined
    "-Xlint:nullary-unit", // nullary methods return Unit
    "-Xlint:inaccessible", // inaccessible types in method signatures
    "-Xlint:nullary-override", // non-nullary def f() overrides nullary def f
    "-Xlint:infer-any", // a type argument is inferred to be Any
    "-Xlint:missing-interpolator", // literal appears to be missing an interpolator id
    "-Xlint:option-implicit", // apply used implicit view
    "-Xlint:package-object-classes", // object defined in package object
    "-Xlint:unsound-match", // may not be typesafe
    "-Xlint:stars-align", // wildcard must align with sequence component
    "-Xlint:constant", // a constant arithmetic expression results in an error
    //"-Xfatal-warnings", // turn warnings into errors
  ),
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
    "org.scalacheck" %% "scalacheck" % "1.16.0" % "test",
    "org.scalactic" %% "scalactic" % "3.2.13",
    "org.scalatest" %% "scalatest" % "3.2.13" % "test",
    "org.scalatestplus" %% "scalacheck-1-16" % "3.2.13.0" % "test"
  ),

  // set logging to show only errors during runs
  logLevel in run := Level.Error,
  logLevel in runMain := Level.Error,

  // JVM arguments: 8G heap size, 2M stack size
  //javaOptions in Test += "-Xmx8G -Xss2M",

  // scoverage options: always build with coverage
  //coverageEnabled := true,

  // scalatest options: -o standard output, D durations
  // -e stderr
  testOptions in Test += Tests.Argument("-e")
)

lazy val lab = (project in file(".")).
  settings(commonSettings: _*)
