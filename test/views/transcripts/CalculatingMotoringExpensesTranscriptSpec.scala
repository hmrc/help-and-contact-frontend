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

import org.jsoup.nodes.Document
import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.transcripts.calculating_motoring_expenses

import scala.collection.JavaConverters._

class CalculatingMotoringExpensesTranscriptSpec extends ViewBehaviours {

  val messageKeyPrefix = "sa.expenses.claiming_motoring_expenses"

  def createView(): Html = inject[calculating_motoring_expenses].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "CalculatingMotoringExpensesTranscript view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "calculating-motoring-expenses-video-transcript"
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
      val doc: Document = asDocument(createView())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Video transcript - Claiming motoring expenses if you’re self-employed"

      val elements: List[String] = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())
      val contentList: List[String] = List(
        "If you work for yourself, you might use your car, van or motorcycle for both business and personal use. " +
          "You can claim business expenses for a number of things, including fuel, vehicle insurance and parking. " +
          "You cannot claim for non-business driving costs, fines or travel between home and work.",
        "There are 2 ways of working out your motor expenses. " +
          "Either by using the actual costs method, or a flat rate for business mileage.",
        "Whichever way you choose, you must continue to use it for that vehicle.",
        "If you use the actual costs method, you’ll need to know how many miles the vehicle has done during the relevant tax year " +
          "– then work out what percentage of those were for business use only.",
        "For example, if the total running costs were £6,000, and 75% of the miles " +
          "were for business use, you would include £4,500 on your Self Assessment tax return.",
        "If you’re using traditional accounting and the actual costs method, " +
          "you can also claim a Capital Allowance for the purchase of the vehicle itself. " +
          "You can find out more about Capital Allowances on GOV.UK.",
        "If you claim expenses using the flat rate for business mileage method, you’ll find the current rates on GOV.UK.",
        "You must keep a record of how many business miles you travel.",
        "The flat rate covers the whole cost of buying, running and maintaining the vehicle – " +
          "so you can’t claim a capital allowance for its purchase or any other motoring expense.",
        "You can find more information about Self Assessment on GOV.UK.",
        "www.gov.uk/expenses-if-youre-self-employed"
      )

      contentList.zip(elements).foreach {
        case (content, element) => element mustBe content
      }
    }
  }

}
