import sbt.Tests.{Group, SubProcess}
import sbt.*

private object AppDependencies {

  import play.core.PlayVersion
  import play.sbt.PlayImport.*

  val playfrontendHmrcVersion = "12.20.0"

  val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc" %% "http-caching-client-play-30" % "11.2.0",
    "uk.gov.hmrc" %% "bootstrap-frontend-play-30"  % "10.2.0",
    "uk.gov.hmrc" %% "tax-year"                    % "4.0.0",
    "uk.gov.hmrc" %% "play-frontend-hmrc-play-30"  % playfrontendHmrcVersion
  )

  def testCommon(): Seq[ModuleID] = {
    val scope: String = "test,it"
    Seq(
      "org.scalatestplus.play"        %% "scalatestplus-play"     % "7.0.1"             % scope,
      "org.jsoup"                     % "jsoup"                   % "1.17.2"            % scope,
      "uk.gov.hmrc"                   %% "bootstrap-test-play-30" % "10.2.0"            % scope,
      "org.playframework"             %% "play-test"              % PlayVersion.current % scope,
      "org.scalacheck"                %% "scalacheck"             % "1.17.0"            % scope,
      "org.scalatestplus"             %% "mockito-3-12"           % "3.2.10.0"          % scope,
      "com.fasterxml.jackson.module"  %% "jackson-module-scala"   % "2.15.3"            % scope,
      "com.vladsch.flexmark"          % "flexmark-all"            % "0.64.8"            % scope
    )
  }

  def test(): Seq[ModuleID] = {
    val scope: String = "test"
    Seq("org.mockito" % "mockito-core" % "5.11.0" % scope)

  }

  def apply(): Seq[ModuleID] =
    compile ++ testCommon() ++ test()
}

object TestPhases {

  def oneForkedJvmPerTest(tests: Seq[TestDefinition]): Seq[Group] =
    tests map { test =>
      new Group(
        test.name,
        Seq(test),
        SubProcess(ForkOptions().withRunJVMOptions(Vector("-Dtest.name=" + test.name)))
      )
    }
}
