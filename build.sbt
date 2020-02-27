ThisBuild / scalaVersion := "2.12.10"
ThisBuild / organization := "com.alejandrohdezma"

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("ci-test", "fix --check; docs/mdoc; +test; +publishLocal; scripted")
addCommandAlias("ci-docs", "docs/mdoc; headerCreateAll")

lazy val `sbt-mdoc-toc-root` = project
  .in(file("."))
  .aggregate(`sbt-mdoc-toc`, `mdoc-toc-generator`)
  .settings(skip in publish := true)

lazy val `docs` = project
  .in(file("sbt-mdoc-toc-docs"))
  .dependsOn(`mdoc-toc-generator`)
  .settings(name := "sbt-mdoc-toc")
  .settings(skip in publish := true)
  .enablePlugins(MdocPlugin)
  .settings(mdocOut := file("."))

lazy val `sbt-mdoc-toc` = project
  .dependsOn(`mdoc-toc-generator`)
  .enablePlugins(SbtPlugin)
  .settings(scriptedLaunchOpts += s"-Dplugin.version=${version.value}")
  .settings(addSbtPlugin("org.scalameta" % "sbt-mdoc" % "[2.0,)" % Provided))

lazy val `mdoc-toc-generator` = project
  .enablePlugins(BuildInfoPlugin)
  .settings(crossScalaVersions := Seq("2.12.10", "2.13.1"))
  .settings(buildInfoPackage := "com.alejandrohdezma.mdoc.toc.generator")
  .settings(libraryDependencies += "org.scalameta" %% "mdoc" % "2.1.1" % Provided)
  .settings(libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % Test)