/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatestplus.mockito.MockitoSugar
import play.api.mvc.{Action, _}
import play.api.test._
import play.api.test.Helpers._
import play.twirl.api.{Html, HtmlFormat}

import scala.concurrent.Future
import config.FrontendAppConfig
import controllers.actions.mocks.MockAuth
import controllers.actions.{AuthAction, ServiceInfoAction}
import models.{HelpCategory, PageType, SaUtr}
import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import org.scalatest.concurrent.ScalaFutures
import services.{PageTypeResolverService, ThresholdService}
import uk.gov.hmrc.http.HeaderCarrier
import views.ViewSpecBase
import views.html.{help_and_contact}

import scala.collection.Seq

class HelpAndContactControllerSpec extends ControllerSpecBase with MockitoSugar with BeforeAndAfter with MockAuth with ViewSpecBase with ScalaFutures {

  implicit val hc: HeaderCarrier = HeaderCarrier()
  val authAction: AuthAction = inject[AuthAction]
  val thresholdService: ThresholdService = inject[ThresholdService]
  val resolverService: PageTypeResolverService = inject[PageTypeResolverService]

  implicit val request: Request[_] = FakeRequest()

  def SUT: HelpAndContactController = inject[HelpAndContactController]

  val helpAndContactView: help_and_contact = inject[help_and_contact]

  def fakeServiceInfoRequest(utr: Option[SaUtr] = None): ServiceInfoRequest[AnyContentAsEmpty.type] =
    ServiceInfoRequest[AnyContentAsEmpty.type](AuthenticatedRequest(FakeRequest("", ""), utr, Some("user@example.com")), Some(HtmlFormat.empty))


  val pageTypeTestCases = Seq(
    "help-with-bta" -> views.html.help_with_your_bta()(messages),
  )

  "HelpAndContactController" should {
    pageTypeTestCases.foreach { case (page: String, expectedContent: Html) =>
    s"return OK for valid pageType: $page" in {

      val result: Future[Result] = SUT.renderPage(page).apply(fakeRequest)

      status(result) mustBe OK
      contentAsString(result) contains expectedContent.body
    }
  }

    "return NOT_FOUND for an invalid pageType" in {
      val invalidPageType = "invalid-page"

      val result: Future[Result] = SUT.renderPage(invalidPageType).apply(fakeRequest)

      status(result) mustBe NOT_FOUND
      contentAsString(result) must include("Page not found")
    }
  }
}
