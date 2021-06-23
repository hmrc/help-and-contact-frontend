package support

import com.github.tomakehurst.wiremock.WireMockServer
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, TestSuite}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import IntegrationTest._
import com.github.tomakehurst.wiremock.client.WireMock

import scala.reflect.ClassTag

trait IntegrationTest extends GuiceOneServerPerSuite
  with BeforeAndAfterAll
  with BeforeAndAfterEach
  with Eventually
  with IntegrationPatience {
  this: TestSuite =>

  final implicit val wiremock: WireMockServer = new WireMockServer(wiremockPort)

  final override implicit lazy val app: Application =
    new GuiceApplicationBuilder()
      .configure(essentialConfigs ++ configOverrides)
      .build()

  def inject[T: ClassTag]: T = app.injector.instanceOf[T]

  final implicit def wsClient: WSClient = inject[WSClient]

  final private def essentialConfigs: Map[String, Any] =
    Map(
      "auditing.enabled" -> "false"
    ) ++ externalMicroservices

  final private def externalMicroservices: Map[String, Any] =
    Set[String](
      "auth",
      "business-tax-account"
    ).map(service => (s"microservice.services.$service.port", wiremockPort)).toMap

  def configOverrides: Map[String, Any] = Map.empty

  override def beforeAll(): Unit = {
    super.beforeAll()
    WireMock.configureFor(wiremockPort)
    wiremock.start()
  }

  override def beforeEach(): Unit = {
    super.beforeEach()
    wiremock.resetAll()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    wiremock.shutdown()
  }

}

object IntegrationTest {
  val wiremockPort = 6002
}
