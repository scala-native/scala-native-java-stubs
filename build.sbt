// Update during release procedure to provide access to staged, but not published artifacts
val StagingRepoIds = 1147 to 1149
val StagingRepoNames = StagingRepoIds.map(id => s"orgscala-native-$id").toSeq

inThisBuild(
  Def.settings(
    version := "1.0.0",
    scalaVersion := "3.1.3",
    crossScalaVersions := Seq("2.12.18", "2.13.13", "3.1.3"),
    publishSettings,
    versionScheme := Some("semver-spec"),
    resolvers ++= StagingRepoNames.flatMap(Resolver.sonatypeOssRepos(_))
  )
)

lazy val javaNetUrl = project
  .withId("java-net-url")
  .in(file("java-net-url"))
  .enablePlugins(ScalaNativePlugin, ScalaNativeJUnitPlugin)
  .settings(withTestUtils)

lazy val javaSecurity = project
  .in(file("java-security"))
  .withId("java-security")
  .enablePlugins(ScalaNativePlugin, ScalaNativeJUnitPlugin)
  .settings(withTestUtils)

lazy val javaxSecurity = project
  .withId("javax-security")
  .in(file("javax-security"))
  .enablePlugins(ScalaNativePlugin)

// Internal utils for projects tests
lazy val testUtils = project
  .in(file("test-utils"))
  .enablePlugins(ScalaNativePlugin, ScalaNativeJUnitPlugin)
  .settings(Compile / publishArtifact := false)

def withTestUtils = Def.settings(
  Test / unmanagedSourceDirectories += (testUtils / sourceDirectory).value
)

def publishSettings = Def.settings(
  organization := "org.scala-native",
  // name := id,
  homepage := Some(url("https://www.scala-native.org")),
  startYear := Some(2024),
  licenses := Seq(
    "BSD-like" -> url("https://www.scala-lang.org/downloads/license.html")
  ),
  developers += Developer(
    email = "wmazur@virtuslab.com",
    id = "wojciechmazur",
    name = "Wojciech Mazur",
    url = url("https://github.com/WojciechMazur")
  ),
  scmInfo := Some(
    ScmInfo(
      browseUrl =
        url("https://github.com/scala-native/scala-native-java-stubs"),
      connection =
        "scm:git:git@github.com:scala-native/scala-native-java-stubs.git"
    )
  ),
  Compile / publishArtifact := true,
  Test / publishArtifact := false,
  publishMavenStyle := true,
  pomIncludeRepository := (_ => false),
  isSnapshot := version.value.contains("SNAPSHOT"),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  credentials ++= {
    for {
      user <- sys.env.get("MAVEN_USER")
      password <- sys.env.get("MAVEN_PASSWORD")
    } yield Credentials(
      realm = "Sonatype Nexus Repository Manager",
      host = "oss.sonatype.org",
      userName = user,
      passwd = password
    )
  }.toSeq
)
