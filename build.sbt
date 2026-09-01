name := "pppl-lab1"

lazy val commonSettings = Seq(
  organization := "edu.colorado.cs",
  version := "4.0.0",

  scalaVersion := "3.6.4",
  scalacOptions ++= Seq(
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-feature", // Emit warning for features that should be imported explicitly
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-explain", // Explain errors in more detail.
    "-explain-types", // Explain type errors in more detail.
    "-Wshadow:all", // Warn when a definition shadows another (Scala 3 successor to -Xlint).
    "-Wimplausible-patterns", // Warn when a pattern comparison can never succeed.
    //"-Wunused:all", // Warn on unused imports, locals, params -- noisy against the ??? stubs.
    //"-Wsafe-init", // Successor to -Xcheckinit; very verbose against ScalaTest specs.
    //"-Werror", // turn warnings into errors
  ),
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-parser-combinators" % "2.4.0",
    "org.scalatest" %% "scalatest" % "3.2.19" % "test"
  ),

  // set logging to show only errors during runs
  logLevel / run := Level.Error,
  logLevel / runMain := Level.Error,
  Global / excludeLintKeys += logLevel,

  // JVM arguments: 8G heap size, 2M stack size
  //javaOptions in Test += "-Xmx8G -Xss2M",

  // scoverage options: always build with coverage
  //coverageEnabled := true,

  // scalatest options: -o standard output, D durations
  // -e stderr
  Test / testOptions += Tests.Argument("-e")
)

lazy val lab1 = (project in file(".")).
  settings(commonSettings: _*)
