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
import views.html.transcripts.paying_your_self_assessment_tax_bill
import collection.JavaConverters._

class PayingYourSelfAssessmentTaxBillViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "payingYourSelfAssessmentTaxBillTranscript"

  def createView = () => inject[paying_your_self_assessment_tax_bill].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "PayingYourSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "paying-sa-tax-bill-transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val ulElements = doc.getElementsByTag("article").first().getElementsByTag("li").asScala.toList.map(_.text())


      val contentList = List(
        "After completing your online Self Assessment return, you’ll see your tax calculation and how much you’ll pay.",
        "There are lots of ways to pay this bill, and 2 deadlines for paying it by. Paying on time means you’ll avoid " +
          "being charged interest and a late penalty. The deadlines are:",
        "To help you avoid missing the deadline, you can choose to make your payments earlier. If you prefer to pay " +
          "regularly throughout the year, use a budget payment plan.",
        "For your payment to reach us the same or next day, pay by:",

        "For payment to reach us within 3 days, pay by:",
        "After paying, you can view your HMRC online account to check payment has been received. It should show as paid" +
          " within 7 working days.",
        "You can find more information about Self Assessment on GOV.UK."
      )
      val contentLIList = List(

        "31 January for your tax bill from the previous year and first payment on account",
        "and 31 July for your second payment on account.",
      "Online or telephone banking, using faster payments",
      "CHAPS",
      "or your debit or corporate credit card online",
      "alternatively you can use a paying-in slip from HMRC at your bank or building society",
        "Direct Debit",
        "or Bacs",
        "alternatively you can send a cheque through the post.",
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

      contentLIList.zipAll(ulElements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}