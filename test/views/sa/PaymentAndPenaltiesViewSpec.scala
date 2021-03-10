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

package views.sa

import models.SaUtr
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.sa.payment_and_penalties

class PaymentAndPenaltiesViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "payment_and_penalties"

  def createView(hasUtr: Option[SaUtr] = None) = () => inject[payment_and_penalties].apply(frontendAppConfig, hasUtr)(HtmlFormat.empty)(fakeRequest, messages)

  "Self Assessment Expenses view" must {

    behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "payment-and-penalties"
    }

    "contain correct heading" in {
      val doc = asDocument(createView()())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Self Assessment: payment and penalties"
    }

    "contain correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("The deadlines for paying are:")

      doc.text() must include("31 January 2021 for your balancing payment. This is for tax year 2019 to 2020")

      doc.text() must include("31 January 2021 for your first payment on account. This is for tax year 2020 to 2021")

      doc.text() must include("31 July 2021 for your second payment on account. This is for tax year 2020 to 2021")

      doc.text() must include("You do not have to wait until 31 January or 31 July to pay. If you do, you might miss the deadline, depending on how you pay.")

      doc.text() must include("Make sure you pay HMRC by the deadline. You’ll be charged interest and may have to")

      doc.text() must include("if your payment is late.")

      doc.text() must include("The time you need to allow depends on how you pay.")

      doc.text() must include("You’ll have a penalty showing on your account if you:")

      doc.text() must include("sent in your tax return late")

      doc.text() must include("paid tax late")

      doc.text() must include("didn’t pay enough tax")

      doc.text() must include("If your business has been affected by coronavirus (COVID-19), you have an extra " +
        "3 months to appeal any penalty dated February 2020 or later.")

      doc.text() must include("You can appeal a penalty if you have a")

      doc.text() must include("You have no penalties or interest to display.")

      doc.text() must include("for example you had an unexpected stay in hospital. You must appeal within 30 days of the date the penalty was sent to you.")

    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "understanding-payments",
        "Understanding balancing payments and payments on account (opens in new tab)",
        "https://www.gov.uk/understand-self-assessment-bill/payments-on-account",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "pay-a-penalty",
        "pay a penalty",
        "https://www.gov.uk/understand-self-assessment-bill/payments-on-account"
      )
      assertLinkById(
        doc,
        "ways-to-pay-taxbill",
        "Read detailed information about ways to pay your tax bill (opens in new tab)",
        "https://www.gov.uk/pay-self-assessment-tax-bill",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "how-do-i-pay-video",
        "Video - How do I pay my Self Assessment tax bill? (opens in new tab)",
        "https://youtu.be/vRxLHyNmWp4",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "how-do-i-pay-transcript",
        "How do I pay my Self Assessment tax bill? - transcript",
        "/business-account/help/transcript/paying-your-self-assessment-tax-bill"
      )
      assertLinkById(
        doc,
        "budget-for-taxbill",
        "Budget for your tax bill if you’re self-employed (opens in new tab)",
        "https://www.gov.uk/self-assessment-ready-reckoner",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "how-do-i-budget-video",
        "Video - How do I budget for my Self Assessment tax bill? (opens in new tab)",
        "https://youtu.be/XaqY3qhDXGo",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "how-do-i-budget-transcript",
        "How do I budget for my Self Assessment tax bill? - video transcript",
        "/business-account/help/transcript/budgeting-your-self-assessment-tax-bill"
      )
      assertLinkById(
        doc,
        "reasonable-excuse",
        "reasonable excuse,",
        "https://www.gov.uk/tax-appeals/reasonable-excuses"
      )
      assertLinkById(
        doc,
        "more-about-sa",
        "Find out more about Self Assessment penalties (opens in new tab)",
        "https://www.gov.uk/tax-appeals/penalty",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "interest-rates",
        "Interest rates for late and early payments (opens in new tab)",
        "https://www.gov.uk/government/publications/rates-and-allowances-hmrc-interest-rates-for-late-and-early-payments",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "interest-rates",
        "Interest rates for late and early payments (opens in new tab)",
        "https://www.gov.uk/government/publications/rates-and-allowances-hmrc-interest-rates-for-late-and-early-payments",
        expectedOpensInNewTab = true
      )

    }


    "have correct links with Sa enrolment" in {
      val doc = asDocument(createView(Some(SaUtr("abcdefgh")))())
      assertLinkById(
        doc,
        "view-sa-penalties",
        "View your Self Assessment penalties",
        "http://localhost:8080/portal/self-assessment/ind/abcdefgh/account/penalties?lang=eng"
      )
      assertLinkById(
        doc,
        "view-sa-interest",
        "View your Self Assessment interest",
        "http://localhost:8080/portal/self-assessment/ind/abcdefgh/account/interests?lang=eng"
      )
    }

  }

}
