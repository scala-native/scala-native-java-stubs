Scala Native implementation of Java stdlib components removed from the core Scala Native project.

## Usage: 

```scala
  libraryDependencies += "org.scala-native" %%% "java-net-url" % "1.0.0"
  libraryDependencies += "org.scala-native" %%% "java-security" % "1.0.0"
  libraryDependencies += "org.scala-native" %%% "javax-security" % "1.0.0"
```

## Modules
### java-net-url
Project providing stubs for `java.net.URI` and `java.net.URL` along with related classes, eg. `URLEncoder`

### java-security
Project providing stubs for multiple interfaces from `java.net.security` and `java.net.security.cert` packages

### javax-security
Project providing stubs `javax.net.security` package, contains  currently only `java.security.auth.x500.X500Principal`
 