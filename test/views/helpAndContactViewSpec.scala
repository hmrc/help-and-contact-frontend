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

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.help_and_contact

import scala.collection.JavaConverters._

class helpAndContactViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "help_and_contact"

  def createView =
    () =>
      inject[help_and_contact].apply(frontendAppConfig)(HtmlFormat.empty)(
        fakeRequest,
        messages
    )

  "Help and contact view" must {

    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "help-and-contact"
    }

    "have correct h2 headings" in {
      val listOfHeadings: List[String] = List(
        "Coronavirus (COVID-19) support",
        "Self Assessment",
        "VAT",
        "Making Tax Digital",
        "Corporation Tax",
        "PAYE for employers"
      )
      val doc = asDocument(createView())
      val headings = doc
        .getElementsByTag("article")
        .first
        .getElementsByTag("h2")
        .asScala
        .toList
        .map(_.text())

      headings mustBe listOfHeadings
    }

    "have the covid section" in {
      val doc = asDocument(createView())
      doc.getElementById("covid-help") must not be null
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "register-or-stopping-self-assessment",
        "Registering or stopping",
        "/business-account/help/self-assessment/register-or-stopping"
      )
      assertLinkById(
        doc,
        "payment-and-penalties-self-assessment",
        "Payment and penalties",
        "/business-account/help/self-assessment/payment-and-penalties"
      )
      assertLinkById(
        doc,
        "expenses",
        "Expenses",
        "/business-account/help/self-assessment/expenses"
      )
      assertLinkById(
        doc,
        "evidence-of-income",
        "Get evidence of your income (SA302)",
        "/business-account/help/self-assessment/evidence-of-income"
      )
      assertLinkById(
        doc,
        "help-with-sa-return",
        "Help with your Self Assessment tax return",
        "/business-account/help/self-assessment/help-with-return"
      )
      assertLinkById(
        doc,
        "contact-about-self-assessment",
        "Contact HMRC about Self Assessment",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/self-assessment"
      )

      assertLinkById(
        doc,
        "register-deregister-vat",
        "Register or deregister for VAT",
        "/business-account/help/vat/register-or-deregister"
      )
      assertLinkById(
        doc,
        "correct-a-mistake",
        "Correct errors on your VAT returns",
        "https://www.gov.uk/vat-corrections"
      )
      assertLinkById(
        doc,
        "how-to-pay-vat",
        "How to pay VAT and deadlines",
        "/business-account/help/vat/how-to-pay"
      )
      assertLinkById(
        doc,
        "contact-about-vat",
        "Contact HMRC about VAT",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/vat-enquiries"
      )

      assertLinkById(
        doc,
        "sing-up-for-mtd",
        "Sign up for Making Tax Digital for VAT",
        "https://www.gov.uk/guidance/sign-up-for-making-tax-digital-for-vat"
      )
      assertLinkById(
        doc,
        "mtd-tax-webniar",
        "Making Tax Digital for VAT webinars",
        "https://www.gov.uk/guidance/help-and-support-for-making-tax-digital"
      )
      assertLinkById(
        doc,
        "contact-about-mtd",
        "Contact HMRC about Making Tax Digital for VAT",
        "https://online.hmrc.gov.uk/webchatprod/community/forums/show/131.page"
      )

      assertLinkById(
        doc,
        "register-or-tell-no-longer-trading-corporation-tax",
        "Register for Corporation Tax, make your company active or tell HMRC you are no longer trading",
        "/business-account/help/corporation-tax/register-or-tell-hmrc-you-are-no-longer-trading"
      )
      assertLinkById(
        doc,
        "how-to-pay-corporation-tax",
        "How to pay your Corporation Tax",
        "/business-account/help/corporation-tax/how-to-pay"
      )
      assertLinkById(
        doc,
        "contact-about-corporation-tax",
        "Contact HMRC about Corporation Tax",
        "/business-account/help/corporation-tax/contact-hmrc"
      )
      assertLinkById(
        doc,
        "ask-for-ct-utr",
        "Ask for a copy of your Corporation Tax UTR (opens in new tab)",
        "http://localhost:9200/ask-for-copy-of-your-corporation-tax-utr",
        true,
        true
      )
      assertLinkById(
        doc,
        "get-started",
        "How to get started with PAYE for employers",
        "/business-account/help/epaye/get-started"
      )
      assertLinkById(
        doc,
        "remove-epaye",
        "Remove PAYE for employers",
        "/business-account/help/epaye/remove"
      )
      assertLinkById(
        doc,
        "view-check-or-correct-epaye",
        "View, check or correct your submissions",
        "/business-account/help/epaye/view-check-correct-submissions"
      )
      assertLinkById(
        doc,
        "paye-and-cis-refunds",
        "PAYE refunds and Construction Industry Scheme (CIS) refunds",
        "/business-account/help/epaye/refunds"
      )
      assertLinkById(
        doc,
        "contact-about-epaye",
        "Contact HMRC about PAYE for employers",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/employer-enquiries"
      )
      assertLinkById(
        doc,
        "contact-about-employee-change",
        "Change employee circumstances",
        "/business-account/help/epaye/change-employee-circumstances"
      )
    }
  }
}
