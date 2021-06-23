/*
 * Copyright 2021 HM Revenue & Customs
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

package views.epaye

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.epaye.view_check_correct_submissions

class ViewCheckCorrectSubmissionsSpec extends ViewBehaviours{
  val messageKeyPrefix = "epaye.submissions"

  def createView = () => inject[view_check_correct_submissions].apply(frontendAppConfig, Some("user@example.com"))(Some(HtmlFormat.empty))(fakeRequest, messages)
  def createViewWithoutEmail = () => inject[view_check_correct_submissions].apply(frontendAppConfig, None)(Some(HtmlFormat.empty))(fakeRequest, messages)

  def doc = asDocument(createView())
  def docWithoutEmail = asDocument(createViewWithoutEmail())

  "View Check Correct Submissions view with an email accessible" must {

    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "view-check-correct-submission"
    }

    "contain correct dynamic content" in {
      doc.text() must include("We send an email to your Government Gateway address each time we receive a Full Payment Submission (FPS) or Employer Payment Summary (EPS).")
      doc.text() must include("Your Government Gateway email is user@example.com")
    }

    "have the correct 'Guide on payroll' link" in {
      assertLinkById(
        doc,
        "payroll-guide",
        "GOV.UK guide on payroll",
        "https://www.gov.uk/running-payroll/fps-after-payday#viewing-late-fps-reports-in-your-hmrc-online-account",
        "link - click:View, check or correct your submissions:GOV.UK guide on payroll",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }

    "have the correct 'Change this address' link" in {
      assertLinkById(
        doc,
        "change-address",
        "Change this address.",
        "http://localhost:9020/business-account/manage-account/government-gateway-warning",
        "link - click:View, check or correct your submissions:Change this address"
      )
    }

    "have the correct 'user guide (view)' link" in {
      assertLinkById(
        doc,
        "view-user-guide","user guide",
        "https://www.gov.uk/government/publications/basic-paye-tools-user-guide/basic-paye-tools-user-guide",
        "link - click:View check or correct your submissions:view user guide",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }

    "have the correct 'user guide (correct)' link" in {
      assertLinkById(
        doc,
        "correct-user-guide","user guide",
        "https://www.gov.uk/government/publications/basic-paye-tools-user-guide/basic-paye-tools-user-guide",
        "link - click:View check or correct your submissions:correct user guide",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }

    "have the correct 'submit an Earlier Year Update' link" in {
      assertLinkById(
        doc,
        "submit-earlier-year-update",
        "submit an Earlier Year Update using Basic PAYE Tools",
        "https://www.gov.uk/government/publications/basic-paye-tools-earlier-year-update-payroll-user",
        "link - click:View check or correct your submissions:submit an Earlier Year Update using Basic PAYE Tools",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }

    "have the correct 'install BPT' link" in {
      assertLinkById(
        doc,
        "install-bpt",
        "install BPT to submit an Earlier Year Update",
        "https://www.gov.uk/government/publications/basic-paye-tools-earlier-year-update-alongside-commercial-software",
        "link - click:View check or correct your submissions:install BPT to submit an Earlier Year Update",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }
  }

  "View Check Correct Submissions view with no email accessible" must {

    behave like normalPage(createView, messageKeyPrefix)

    "contain correct dynamic content" in {
      docWithoutEmail.text() must include("We can send you an email each time we receive a Full Payment Submission (FPS) or Employer Payment Summary (EPS), if you")
      docWithoutEmail.text() must include("to your Government Gateway account.")
    }

    "have the correct 'Add an email' link" in {
      assertLinkById(
        docWithoutEmail,
        "add-email",
        "add an email",
        "http://localhost:9020/business-account/manage-account/government-gateway-warning",
        "link - click:View check or correct your submissions:add an email"
      )
    }
  }
}
