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

package services

import controllers.ControllerSpecBase
import controllers.actions.mocks.MockAuth
import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import org.scalatestplus.mockito.MockitoSugar
import play.api.i18n.Messages
import play.twirl.api.HtmlFormat
import models.{PageType, SaUtr}
import org.scalatest.concurrent.ScalaFutures
import play.api.mvc.AnyContent
import play.api.test.FakeRequest

class PageTypeResolverServiceSpec extends ControllerSpecBase with MockitoSugar with MockAuth with ScalaFutures {

  implicit val message: Messages = messages

  def fakeServiceInfoRequest(utr: Option[SaUtr] = None, email: Option[String] = Some("user@example.com")): ServiceInfoRequest[AnyContent] =
    ServiceInfoRequest[AnyContent](AuthenticatedRequest(FakeRequest("", ""), utr, email), Some(HtmlFormat.empty))

  "PageTypeResolverService" should {

    "return the correct HTML for HelpWithBTA PageType" in {
      val testUtr = Some(SaUtr("abcdefgh"))
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.HelpWithBTA
      val result = service.resolve(pageType)(fakeServiceInfoRequest(testUtr), message)
      val expectedContent: HtmlFormat.Appendable = views.html.general.help_with_your_bta(pageType.name, frontendAppConfig)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for RegisteringOrStopping PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.RegisteringOrStopping
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.sa.registering_or_stopping(pageType.name, frontendAppConfig)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for PaymentsAndPenalties PageType" in {
      val testUtr = Some(SaUtr("abcdefgh"))
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.PaymentsAndPenalties
      val result = service.resolve(pageType)(fakeServiceInfoRequest(testUtr), message)
      val expectedContent: HtmlFormat.Appendable = views.html.sa.payments_and_penalties(pageType.name, frontendAppConfig, testUtr)(Some(HtmlFormat.empty))(fakeRequest, messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for HelpWithSATaxReturn PageType" in {
      val testUtr = Some(SaUtr("abcdefgh"))
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.HelpWithSATaxReturn
      val result = service.resolve(pageType)(fakeServiceInfoRequest(testUtr), message)
      val expectedContent: HtmlFormat.Appendable = views.html.sa.help_with_sa_tax_return(pageType.name, testUtr, frontendAppConfig)(fakeRequest, messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for GetEvidenceOfIncome PageType" in {
      val testUtr = Some(SaUtr("abcdefgh"))
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.GetEvidenceOfIncome
      val result = service.resolve(pageType)(fakeServiceInfoRequest(testUtr), message)
      val expectedContent: HtmlFormat.Appendable = views.html.sa.get_evidence_of_income(pageType.name, frontendAppConfig, testUtr.isDefined)(Some(HtmlFormat.empty))(fakeRequest, messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for Expenses PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.Expenses
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.sa.expenses(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return empty HTML for SignUpForMTD PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.SignUpForMTD
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      result.body mustBe HtmlFormat.empty.body
    }

    "return the correct HTML for ChangeContactAndAccountDetails PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.ChangeContactAndAccountDetails
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.general.change_contact_and_account_details(pageType.name, frontendAppConfig)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for HowToAddTax PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.HowToAddTax
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.general.how_to_add_tax(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for RegisterOrDeregisterVAT PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.RegisterOrDeregisterVAT
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.vat.register_or_deregister(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for HowToPayVatAndDeadlines PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.HowToPayVatAndDeadlines
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.vat.how_to_pay_vat_and_deadlines(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for CorrectingErrorsOnReturns PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.CorrectingErrorsOnReturns
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.vat.correcting_errors_on_returns(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for GetStarted PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.GetStarted
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.epaye.epaye_get_started(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for ViewOrCorrectYourSubmissions PageType" in {
      val testEmail = Some("testUser@test.com")
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.ViewOrCorrectYourSubmissions
      val result = service.resolve(pageType)(fakeServiceInfoRequest(email = testEmail), message)
      val expectedContent: HtmlFormat.Appendable = views.html.epaye.epaye_view_or_correct_submissions(pageType.name, testEmail)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for PayeCisRefunds PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.PayeCisRefunds
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.epaye.paye_cis_refunds(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for PayeChangeCircumstance PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.PayeChangeCircumstance
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.epaye.paye_change_circumstance(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for PayeStopEmployer PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.PayeStopEmployer
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.epaye.paye_stop_employer(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for RegisterAddCT PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.RegisterAddCT
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.ct.ctax_register_add(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for HowToPayCT PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.HowToPayCT
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.ct.ctax_how_to_pay(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for ClosingLimitedCompanyCT PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.ClosingLimitedCompanyCT
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.ct.ctax_closing_limited_company(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for GetUtrCT PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.GetUtrCT
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.ct.ctax_ask_utr_corporation_tax(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }

    "return the correct HTML for ContactHMRC PageType" in {
      val service = new PageTypeResolverService(frontendAppConfig)
      val pageType = PageType.ContactHMRC
      val result = service.resolve(pageType)(fakeServiceInfoRequest(), message)
      val expectedContent: HtmlFormat.Appendable = views.html.general.contact_hmrc(pageType.name)(messages)
      result.body mustBe expectedContent.body
    }
  }
}