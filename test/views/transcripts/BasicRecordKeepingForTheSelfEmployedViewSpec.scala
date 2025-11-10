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

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.transcripts.basic_record_keeping

import scala.collection.JavaConverters._

class BasicRecordKeepingForTheSelfEmployedViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "basic.record.keeping.transcript"

  def createView = () => inject[basic_record_keeping].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "BudgetingYourSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain one heading" which {
      val doc = asDocument(createView())
      val h1 = doc.getElementsByTag("h1")

      "has the correct id" in {
        h1.attr("id") mustBe "basic-record-keeping-transcript"
      }

      "has the correct text" in {
        h1.text() mustBe "Video transcript - What self-employment records do I need to keep?"
      }
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/")
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "Basic record keeping is an important activity for any business. You must record any money going in and out of your business, whether the transaction is made in person - by phone - or online.",
        "You can keep your records on paper or digitally using a computer. These include items such as invoices, sale receipts, proof of purchases and bank statements. Remember your records must be accurate, clear and complete.",
        "Some software providers offer free record keeping apps for small businesses. How you keep your records depends on if you use the traditional or cash basis method of accounting, and how you work out your expenses. There’s more information about cash basis and simplified expenses in our YouTube video and on GOV.UK.",
        "It’s easier if you organise your income and expenses into categories and update these regularly. And remember to back up your digital records.",
        "It’s important to keep your business and personal transactions separate. Drawing or using your businesses money for personal use isn’t an allowable business expense.",
        "If you’re VAT registered, you’ll also need to keep records of how much VAT you’ve paid to suppliers and charged your customers. From April 2022, all VAT registered businesses must follow the rules for Making Tax Digital for VAT and submit their records using Making Tax Digital software.",
        "Remember to store your records for at least six years, as we may need them to check you’re paying the right amount of tax.",
        "You can find out more on GOV.UK and in our other helpful videos on YouTube."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}