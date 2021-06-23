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

package views.transcripts

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.transcripts.budgeting_your_self_assessment_tax_bill
import collection.JavaConverters._

class BudgetingYourSelfAssessmentTaxBillViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "budgetingYourSelfAssessmentTaxBillTranscript"

  def createView = () => inject[budgeting_your_self_assessment_tax_bill].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "BudgetingYourSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "budgeting-your-sa-tax-bill-transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "Budgeting for your SA tax bill can help you pay what you owe on time. You don’t have to wait until 31 January or 31 July to pay. If you do, you could miss the deadline.",
        "You can set up a budget plan to make regular payments by direct debit. If you’re up to date with previous Self Assessment payments, you can set up a budget payment plan using your online account on GOV.UK.",
        "It’s flexible so you can decide how much you pay each month. You can reduce or stop payments at any time.",
        "If you’re self-employed, our ready reckoner on GOV.UK can help you budget for tax and National Insurance. Put in your estimated weekly or monthly profit, and we’ll give you an idea of how much you have to pay.",
        "A budget payment plan can help you meet the payment deadlines, and avoid any fines.",
        "Remember, if there’s anything left to pay by 31 January, you’ll need to pay it off by that date.",
        "To find out more about Self Assessment, go to GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}