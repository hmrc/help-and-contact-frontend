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

package views

import models.PageType
import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.epaye.paye_change_circumstance

class PayeChangesEmployeeCircumstancesViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "changes-in-employee-circumstances"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  paye_change_circumstance.apply(PageType.PayeChangeCircumstance.name)(messages)


  "Paye Changes in employee circumstances view" must {

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-changes-in-employee-circumstances"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Get maternity or other parental leave pay in advance")
      headings must include("Sending employees to work abroad")
      headings must include("Get HMRC to pay for an employee tax refund")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("If an employee has paid too much PAYE you may need to refund them.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByLinkText(
        doc,
        "paid paternal leave (opens in new tab)",
        "https://www.gov.uk/maternity-paternity-calculator"
      )
      assertLinkByLinkText(
        doc,
        "reclaim the payments (opens in new tab)",
        "https://www.gov.uk/recover-statutory-payments"
      )
      assertLinkByLinkText(
        doc,
        "ask HMRC to refund you before you make the payments (opens in new tab)",
        "https://www.gov.uk/recover-statutory-payments/if-you-cant-afford-to-make-payments"
      )
      assertLinkByLinkText(
        doc,
        "form CA3821",
        "https://www.gov.uk/government/publications/national-insurance-sending-employees-to-work-abroad-ca3821"
      )
      assertLinkByLinkText(
        doc,
        "form CA3822",
        "https://www.gov.uk/guidance/tell-hmrc-about-employees-going-to-work-in-the-european-economic-area-ca3822"
      )
      assertLinkByLinkText(
        doc,
        "ask HMRC to reimburse you",
        "/digital-forms/form/Application-for-funding-a-tax-refund-for-an-employee/insufficient-enrolments"
      )
    }
  }
}
