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

package views.sa

import config.FrontendAppConfig
import models.{PageType, SaUtr}
import play.twirl.api.{HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.sa.payments_and_penalties

class PaymentAndPenaltiesViewSpec extends ViewBehaviours {

  val messageKeyPrefix                  = "payments_and_penalties"
  lazy val appConfig: FrontendAppConfig = inject[FrontendAppConfig]

  def createView(hasUtr: Option[SaUtr] = None) =
    payments_and_penalties(PageType.PaymentsAndPenalties.name, frontendAppConfig, hasUtr)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "Self Assessment Expenses view" must {

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "payment-and-penalties"
    }

    "contain correct heading" in {
      val doc = asDocument(createView())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Self Assessment: Payment and penalties"
    }

    "contain correct content" in {
      val doc = asDocument(createView())
      doc.text() must include("The deadlines for paying are:")

      doc.text() must include(
        s"31 January ${appConfig.taxYearNext} for your balancing payment. " +
          s"This is for tax year ${appConfig.taxYearPrevious} to ${appConfig.taxYearBegin}"
      )

      doc.text() must include(
        s"31 January ${appConfig.taxYearNext} for your first payment on account." +
          s" This is for tax year ${appConfig.taxYearBegin} to ${appConfig.taxYearNext}"
      )

      doc.text() must include(
        s"31 July ${appConfig.taxYearNext} for your second payment on account. " +
          s"This is for tax year ${appConfig.taxYearBegin} to ${appConfig.taxYearNext}"
      )

      doc.text() must include(
        "You do not have to wait until 31 January or 31 July to pay. If you do, you might miss the deadline, depending on how you pay."
      )

      doc.text() must include("Make sure you pay HMRC by the deadline. You’ll be charged interest and may have to")

      doc.text() must include("if your payment is late.")

      doc.text() must include("The time you need to allow depends on how you pay.")

      doc.text() must include("You’ll have a penalty showing on your account if you:")

      doc.text() must include("use the online service to appeal a late filing penalty")

      doc.text() must include("paid tax late")

      doc.text() must include("didn’t pay enough tax")

      doc.text() must include("You can appeal a penalty if you have a")

      doc.text() must include("You have no penalties or interest to display.")

      doc.text() must include(
        "You must appeal within 30 days of the date the penalty was sent to you."
      )

    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "understanding-payments",
        "Understanding balancing payments and payments on account",
        "https://www.gov.uk/understand-self-assessment-bill/payments-on-account",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "pay-a-penalty",
        "pay a penalty",
        "https://www.gov.uk/understand-self-assessment-bill/payments-on-account",

      )
      assertLinkById(
        doc,
        "ways-to-pay-taxbill",
        "Read detailed information about ways to pay your tax bill",
        "https://www.gov.uk/pay-self-assessment-tax-bill",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "how-do-i-pay-video",
        "Video - How do I pay my Self Assessment tax bill? (opens in new tab)",
        "https://www.youtube.com/watch?v=9B9CBv5xgKs",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "budget-for-taxbill",
        "Budget for your tax bill if you’re self-employed",
        "https://www.gov.uk/self-assessment-ready-reckoner",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "how-do-i-budget-video",
        "Video - How do I budget for my Self Assessment tax bill? (opens in new tab)",
        "https://www.youtube.com/watch?v=sGpQ7ZT3tMs",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "reasonable-excuse",
        "reasonable excuse",
        "https://www.gov.uk/tax-appeals/reasonable-excuses",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "more-about-sa",
        "Find out more about Self Assessment penalties",
        "https://www.gov.uk/tax-appeals/penalty",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "interest-rates",
        "Interest rates for late and early payments",
        "https://www.gov.uk/government/publications/rates-and-allowances-hmrc-interest-rates-for-late-and-early-payments",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "interest-rates",
        "Interest rates for late and early payments",
        "https://www.gov.uk/government/publications/rates-and-allowances-hmrc-interest-rates-for-late-and-early-payments",

        expectedOpensInNewTab = false
      )

    }

    "have correct links with Sa enrolment" in {
      val doc = asDocument(createView(Some(SaUtr("1234567800"))))
      assertLinkById(
        doc,
        "view-sa-penalties",
        "View your Self Assessment penalties",
        "http://localhost:8081/portal/self-assessment/ind/1234567800/account/penalties?lang=eng",

      )
      assertLinkById(
        doc,
        "view-sa-interest",
        "View your Self Assessment interest",
        "http://localhost:8081/portal/self-assessment/ind/1234567800/account/interests?lang=eng",

      )
      assertLinkById(
        doc,
        "appeal-sa-late",
        "use the online service to appeal a late filing penalty",
        "http://localhost:9091/digital-forms/form/self-assessment-appeal-late-filing-penalty/draft/guide",

      )
      assertLinkById(
        doc,
        "appeal-filing",
        "use form SA370 to appeal any late filing or late payment penalty",
        "https://www.gov.uk/government/publications/self-assessment-appeal-against-penalties-for-late-filing-and-late-payment-sa370",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "write-hmrc",
        "write to HMRC",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/self-assessment",

      )
    }
  }
}
