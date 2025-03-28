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

package views.transcripts

import org.jsoup.Jsoup
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.transcripts.budgeting_your_self_assessment_tax_bill

import scala.collection.JavaConverters._

class BudgetingYourSelfAssessmentTaxBillViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "budgetingYourSelfAssessmentTaxBillTranscript"

  def createView = () => inject[budgeting_your_self_assessment_tax_bill].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "BudgetingYourSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "budgeting-your-sa-tax-bill-transcript"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-uk-pay-sa-bill",
        "set up a budget plan (opens in a new tab)",
        "https://www.gov.uk/pay-self-assessment-tax-bill/pay-weekly-monthly",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "gov-uk-sa-tax-calculator",
        "Self Assessment tax calculator (opens in a new tab)",
        "https://www.gov.uk/self-assessment-tax-calculator",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "gov-uk-sa-link",
        "Self Assessment on GOV.UK (opens in a new tab)",
        "https://www.gov.uk/browse/tax/self-assessment",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "hmrc-youtube-channel",
        "helpful videos on YouTube (opens in a new tab).",
        "https://www.youtube.com/@HMRCgovuk",

        expectedOpensInNewTab = true
      )
    }
    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "Budgeting for your Self Assessment tax bill can help you pay what you owe by the deadline, and also avoid any interest and penalties.",
        "You don’t have to wait until the 31st of January or the 31st of July to pay. If you do, you could miss the deadline.",
        "A budget payment plan allows you to make regular payments by direct debit towards your next tax bill.",
        "If you’re up to date with previous Self Assessment payments, you can set up a budget plan (opens in a new tab) using your online account on GOV.UK.",
        "It’s flexible so you can decide how much you pay each month. You can reduce or stop payments at any time.",
        "When you set up a budget payment plan, it’s a good idea to include an end date for your payments, for example, the 31st of January.",
        "If you don’t, any repayment you’re due may be delayed.",
        "To help you decide what to pay, use the Self Assessment tax calculator (opens in a new tab) on GOV.UK to estimate your Self Assessment tax bill for the current year.",
        "Tell us your estimated income to get an idea of how much Income Tax and any Class 4 National Insurance you need to pay.",
        "As well as using a budget payment plan, you can also make regular one-off payments through your online bank account - by using faster payments, setting up single Direct Debits or by sending a cheque.",
        "Remember, if there’s anything left to pay, you’ll need to do this by the 31st of January.",
        "There’s more about Self Assessment on GOV.UK (opens in a new tab) and in our other helpful videos on YouTube (opens in a new tab)."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}