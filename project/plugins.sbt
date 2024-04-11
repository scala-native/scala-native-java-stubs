// Update during release procedure to provide access to staged, but not published artifacts
val StagingRepoIds = 1147 to 1149
val StagingRepoNames = StagingRepoIds.map(id => s"orgscala-native-$id").toSeq
resolvers ++= StagingRepoNames.flatMap(Resolver.sonatypeOssRepos(_))

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.0")
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.0")
