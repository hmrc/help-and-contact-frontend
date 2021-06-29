package routes.transcripts

import org.jsoup.nodes.Document
import org.scalatest.{FeatureSpec, GivenWhenThen, MustMatchers}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import support.JsoupHelper._
import support.stubs.StubAuth.mockAuthorised
import support.stubs.StubBta.{mockGetNavLinks, mockGetServiceInfo}
import support.{HttpRequest, IntegrationTest}

class CalculatingMotoringExpensesPageISpec
    extends FeatureSpec
    with MustMatchers
    with GivenWhenThen
    with IntegrationTest {

  feature("Calculating motoring expenses page") {
    scenario("loads correctly") {
      Given("user is authorised")
      mockAuthorised()

      And("service info loads correctly")
      mockGetServiceInfo()
      mockGetNavLinks()

      When("Calculating motoring expenses page is called")
      val result: WSResponse =
        HttpRequest.get("/transcript/calculating-motoring-expenses")

      Then("we get an OK response")
      result.status mustBe OK

      Then("we get the Calculating motoring expenses page")
      val doc: Document = result.bodyAsDom

      // todo add an id to the page for test purposes instead of using content
      doc
        .getElementById("calculating-motoring-expenses-video-transcript")
        .text() mustBe "Claiming motoring expenses if you’re self-employed - video transcript"
    }
  }

}
