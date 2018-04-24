/*
 * Copyright 2018 HM Revenue & Customs
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

import controllers.actions._
import handlers.ErrorHandler
import models.HelpCategory
import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.Helpers._
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.domain.SaUtr
import views.html.sa.{how_to_pay_self_assessment, register_deregister, _}
import views.html.vat.{payments_and_deadlines, questions_about_vat}


class HelpAndContactControllerSpec extends ControllerSpecBase {

  def fakeServiceInfoRequest(utr: Option[SaUtr] = None) = ServiceInfoRequest(AuthenticatedRequest(fakeRequest, utr), HtmlFormat.empty)

  def pageRouter(helpCategory: HelpCategory, page: String, view: () => HtmlFormat.Appendable) = {
    "HelpAndContactController onPageLoad" must {
      s"display the correct $helpCategory view for /$page" in {
        val result = controller().onPageLoad(helpCategory, page).apply(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe view().toString
      }
    }
  }

  def pageRouterWithEnrolments(helpCategory: HelpCategory, page: String, view: () => HtmlFormat.Appendable,
                 requestToApply: ServiceInfoRequest[AnyContentAsEmpty.type], controller: HelpAndContactController) = {
    "HelpAndContactController onPageLoad" must {
      s"display the correct $helpCategory view for /$page" in {
        val result = controller.onPageLoad(helpCategory, page)(requestToApply)
        status(result) mustBe OK
        contentAsString(result) mustBe view().toString
      }
    }
  }

  def controller() =
    new HelpAndContactController(frontendAppConfig, messagesApi, FakeAuthAction, FakeServiceInfoAction, injector.instanceOf[ErrorHandler])

  "HelpAndContact Controller" must {

    "return 404 for a page that does not exist" in {
      HelpCategory.values.foreach { category =>
        val result = controller().onPageLoad(category, "abcdefgh").apply(fakeRequest)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  behave like pageRouter(
    HelpCategory.VAT,
    "how-to-pay",
    () => payments_and_deadlines(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.VAT,
    "questions",
    () => questions_about_vat(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.SelfAssessment,
    "register-or-deregister",
    () => register_deregister(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.SelfAssessment,
    "how-to-pay",
    () => how_to_pay_self_assessment(frontendAppConfig)(HtmlFormat.empty)(fakeServiceInfoRequest(), messages)
  )

  "behave appropriately for enrolments" when {
    "the user has no enrolments" must {
      behave like pageRouterWithEnrolments(
        HelpCategory.SelfAssessment,
        "evidence-of-income",
        () => sa_evidence(frontendAppConfig, false, "http://localhost:9020/business-account/self-assessment")
          (HtmlFormat.empty)
          (fakeServiceInfoRequest(), messages),
        fakeServiceInfoRequest(),
        controller()
      )
    }

    "the user has an SA enrolment" must {

      val testUtr = Some(SaUtr("testUtr"))
      def controllerWithEnrolment() =
        new HelpAndContactController(frontendAppConfig, messagesApi, FakeAuthActionWithSaEnrolment(testUtr),
          FakeServiceInfoAction, injector.instanceOf[ErrorHandler])

      behave like pageRouterWithEnrolments(
        HelpCategory.SelfAssessment,
        "evidence-of-income",
        () => sa_evidence(frontendAppConfig, true, "http://localhost:9020/business-account/self-assessment")
          (HtmlFormat.empty)
          (fakeServiceInfoRequest(testUtr), messages),
        fakeServiceInfoRequest(testUtr),
        controllerWithEnrolment()
      )
    }

  }
}
