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

import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.transcripts.expenses_if_you_are_self_employed

import scala.collection.JavaConverters._

class ExpensesIfYouAreSelfEmployedTranscriptSpec extends ViewBehaviours {

  val messageKeyPrefix = "sa.expenses.what_expenses_can_i_include_in_my_sa_tax_return_transcript"

  def createView(): Html = inject[expenses_if_you_are_self_employed].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "ExpensesIfYouAreSelfEmployedTranscript view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "what-expenses-can-i-include-in-my-sa-tax-return-video-transcript"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/",
        expectedOpensInNewTab = true
      )
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Video transcript – What expenses can I include in my Self Assessment tax return?"

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())
      val bullets = doc.getElementsByTag("article").first().getElementsByTag("li").asScala.toList.map(_.text())

      val bulletList = List(
        "office property and equipment",
        "travel, including cars and vans",
        "staff",
        "any legal and financial costs",
        "and also marketing and subscriptions.",
        "your home and accommodation",
        "food",
        "clothing",
        "holidays",
        "and anything you buy for you and your family."
      )

      val contentList = List(
        "You’re self-employed – which means you have essential costs to keep your business running. You can deduct these " +
          "costs – which are called allowable expenses – to work out your taxable profit.",
        "But remember – if you’re using the one thousand pound tax-free trading allowance, you can’t claim any expenses.",
        "Allowable expenses include:",
        "You can’t claim for anything you spend on personal things, such as:",
        "You may want to use simplified expenses – this means you claim a flat rate instead of working out the actual " +
          "costs. For example, for car travel you can claim 45 pence a mile up to 10,000 miles and then 25 pence a mile over this amount.",
        "If your income is eighty five thousand pounds or more, you’ll need to list each expense individually under the " +
          "appropriate heading. This also applies if you didn’t trade a full year but would have earned this amount or " +
          "more if you had – for example, your income for six months was fifty thousand pounds – so in 12 months it " +
          "would have been a hundred thousand pounds.",
        "If your income is less than eighty five thousand pounds, it’s up to you whether you give us your total amount " +
          "of expenses or list them individually.",
        "It’s important you keep records and proof of your business costs – you’ll need them to complete your Self Assessment tax return."+
        " Sometimes we ask to see these, but please don’t send them with your return.",
        "You can find more information about Self Assessment on GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

      bulletList.zipAll(bullets, "", "").foreach {
        case (expected, actual) => actual mustBe expected
      }
    }
  }
}
