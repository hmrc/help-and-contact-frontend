package routes

import java.net.URLEncoder

import config.FrontendAppConfig
import org.jsoup.nodes.Document
import org.scalatest._
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import support.{HttpRequest, IntegrationTest}
import support.stubs.StubAuth._
import support.stubs.StubBta._
import support.JsoupHelper._

class MainPageISpec extends FeatureSpec with MustMatchers with GivenWhenThen with IntegrationTest {

  feature("Help and Contact Page") {
    scenario("loads correctly") {
      Given("User is authorised")
      mockAuthorised()

      And("Service info is available")
      mockGetServiceInfo()
      mockGetNavLinks()

      When("the Main page is accessed")
      val result: WSResponse = HttpRequest.get("/")

      Then("we get an OK response")
      result.status mustBe OK

      Then("we get the main page with the service info bar")
      val doc: Document = result.bodyAsDom

      doc.getElementById("help-and-contact").text() mustBe "Help and contact"
      //TODO Sort this when we have migrated the partial over in BTA
//      doc.select(".service-info-list").first().children().size() must not be 0
    }

    scenario("Unauthorised user") {
      Given("User is unauthorised")
      mockUnauthorised()


      And("Service info is available")
      mockGetServiceInfo()
      mockGetNavLinks()

      When("the Main page is accessed")
      val result: WSResponse = HttpRequest.get("/")

      Then("we get an SEE_OTHER response")
      result.status mustBe SEE_OTHER

      And("redirects to the correct location determined by the settings in application.conf")
      val config = inject[FrontendAppConfig]
      val expected = s"${config.loginUrl}?continue=${URLEncoder.encode(config.loginContinueUrl, "UTF-8")}"
      result.header(LOCATION).get mustBe expected
    }

    scenario("Navigation bar ist kaput") {
      Given("User is authorised")
      mockAuthorised()

      And("Service info is not available")
      mockGetServiceInfoFailure()
      mockGetNavLinks()

      When("the Main page is accessed")
      val result: WSResponse = HttpRequest.get("/")

      Then("we get an OK response")
      result.status mustBe OK

      Then("we get the main page without the service info bar")
      val doc: Document = result.bodyAsDom

      doc.getElementById("help-and-contact").text() mustBe "Help and contact"
      //TODO Sort this when we have migrated the partial over in BTA

      //      doc.select(".service-info").first().children().size() mustBe 0
    }
  }

}
