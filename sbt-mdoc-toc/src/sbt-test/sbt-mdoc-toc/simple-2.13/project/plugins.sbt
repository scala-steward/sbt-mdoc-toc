addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.1.3")
sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.alejandrohdezma" % "sbt-mdoc-toc" % x)
  case _       => sys.error("https://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html")
}