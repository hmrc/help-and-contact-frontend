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

package views.ct

import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.twirl.api.HtmlFormat
import models.SaUtr
import views.behaviours.ViewBehaviours
import views.html.ct.how_to_pay_corporation_tax

class HowToPayCorporationTaxViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ct.how_to_pay"

  def fakeServiceInfoRequest(saUtr: Option[SaUtr] = None) = {
    ServiceInfoRequest(AuthenticatedRequest(fakeRequest, saUtr, None), Some(HtmlFormat.empty))
  }

  def createView(saUtr: Option[SaUtr] = None) = () => inject[how_to_pay_corporation_tax].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeServiceInfoRequest(saUtr), messages)

  "HowToPayCorporationTax view" must {
    behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "how-to-pay-ct"
    }

    "have correct h2 headings" in {
      val doc = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Deadlines")
      headings must include("If your taxable profits are up to £1.5 million")
      headings must include("If your taxable profits are more than £1.5 million")
      headings must include("Ways to pay")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("The deadline for your payment will depend on your taxable profits.")
      doc.text() must include("You must pay your Corporation Tax 9 months and 1 day after the end of your accounting period. " +
        "Your accounting period is usually your financial year, but you may have 2 accounting periods in ")
      doc.text() must include("You must pay your Corporation Tax ")
      doc.text() must include("Make sure you pay HM Revenue and Customs (HMRC) by the deadline. They may")
      doc.text() must include(" if you do not pay on time. They will ")
      doc.text() must include(" if you pay your tax early.")
      doc.text() must include("You can no longer pay at the Post Office. You cannot pay Corporation Tax by post.")
      doc.text() must include("The time you need to allow depends on how you pay.")
      doc.text() must include("If you are paying your bill the same or the next day you can use")
      doc.text() must include("Faster Payments")
      doc.text() must include("If you have longer to pay your bill you can use")
      doc.text() must include("(3 working days)")
      doc.text() must include("your")
      doc.text() must include("(3 working days if you already have one set up, 5 working days if you need to set one up)")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "year-company-set-up",
        "the year you set up your company",
        "https://www.gov.uk/first-company-accounts-and-return",
        "link - click:How to pay your Corporation Tax:the year you set up your company",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "instalments",
        "in instalments",
        "https://www.gov.uk/guidance/corporation-tax-paying-in-instalments",
        "link - click:How to pay your Corporation Tax:in instalments",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "charge-you-interest",
        "charge you interest",
        "https://www.gov.uk/guidance/corporation-tax-interest-charges",
        "link - click:How to pay your Corporation Tax:charge you interest",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "pay-you-interest",
        "pay you interest",
        "https://www.gov.uk/get-refund-interest-corporation-tax",
        "link - click:How to pay your Corporation Tax:pay you interest",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "online-or-telephone-banking",
        "online or telephone banking",
        "https://www.gov.uk/pay-corporation-tax/bank-details",
        "link - click:How to pay your Corporation Tax:Faster Payments",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "chaps",
        "CHAPS",
        "https://www.gov.uk/pay-corporation-tax/bank-details",
        "link - click:How to pay your Corporation Tax:CHAPS",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "debit-or-credit-card",
        "online by debit or credit card",
        "https://www.gov.uk/pay-corporation-tax/debit-or-credit-card",
        "link - click:How to pay your Corporation Tax:online by debit or credit card",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "bank-or-building-society",
        "bank or building society",
        "https://www.gov.uk/pay-corporation-tax/bank-or-building-society",
        "link - click:How to pay your Corporation Tax:bank or building society",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "bacs",
        "Bacs",
        "https://www.gov.uk/pay-corporation-tax/bank-details",
        "link - click:How to pay your Corporation Tax:Bacs",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "direct-debit",
        "Direct Debit",
        "https://www.gov.uk/pay-corporation-tax/direct-debit",
        "link - click:How to pay your Corporation Tax:Direct Debit",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }
  }
}
