ThisBuild / scalaVersion := "2.12.11"
ThisBuild / organization := "com.alejandrohdezma"

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("ci-test", "fix --check; mdoc; +test; +publishLocal; scripted")
addCommandAlias("ci-docs", "github; mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

//These are the only external dependencies
val `sbt-mdoc` = "org.scalameta" % "sbt-mdoc" % "[2.0,)" % Provided // scala-steward:off
val mdoc       = "org.scalameta" %% "mdoc"    % "[2.0,)" % Provided // scala-steward:off

skip in publish := true

lazy val `docs` = project
  .in(file("sbt-mdoc-toc-docs"))
  .enablePlugins(MdocPlugin)
  .dependsOn(`mdoc-toc-generator`)
  .settings(skip in publish := true)
  .settings(mdocOut := file("."))

lazy val `sbt-mdoc-toc` = project
  .enablePlugins(SbtPlugin)
  .dependsOn(`mdoc-toc-generator`)
  .settings(scriptedLaunchOpts += s"-Dplugin.version=${version.value}")
  .settings(addSbtPlugin(`sbt-mdoc`))

lazy val `mdoc-toc-generator` = project
  .enablePlugins(BuildInfoPlugin)
  .settings(crossScalaVersions := Seq("2.12.11", "2.13.1"))
  .settings(buildInfoPackage := "com.alejandrohdezma.mdoc.toc.generator")
  .settings(libraryDependencies += mdoc)
  .settings(libraryDependencies += "org.specs2" %% "specs2-core" % "4.9.3" % Test)
