name := "RiotLOLClient"
organization := "robin"

scalaVersion := "2.13.6"
scalacOptions := Settings.compilerOptions
semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / scalafixScalaBinaryVersion := "2.13"

version := (ThisBuild / version).value

publishMavenStyle := true
publishConfiguration := publishConfiguration.value.withOverwrite(true)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
libraryDependencies ++= Libs.libraryDependencies


// Dependency check settings
dependencyCheckOutputDirectory := Some(file("target/dependency-check"))
dependencyCheckFormat := "ALL"
dependencyCheckCentralAnalyzerEnabled := Some(true)
dependencyCheckAutoUpdate := Some(false)
