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

import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.transcripts.cash_basis

import scala.collection.JavaConverters._

class CashBasisViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "cash_basis"

  def createView: () => Html =
    () =>
      inject[cash_basis].apply(frontendAppConfig)(Some(HtmlFormat.empty))(
        fakeRequest,
        messages
    )

  "RegisteringForSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .attr("id") mustBe "cash-basis-transcript"
    }

    "have correct content" in {
      val bulletPointList = List(
        "some vehicles",
        "working from home",
        "and living at your business premises."
      )

        val bulletPointList2 = List(
          "your vehicle’s business miles",
          "the hours you work at home",
          "and how many people live at your business premises over the year"
        )

      val contentList: List[String] = List(
        "Cash basis and simplified expenses are two income tax schemes that can make life easier for self-employed businesses.",
        "Instead of using traditional accounting rules, cash basis can be used if you’re a self-employed " +
          "business or partnership with a total annual business income of £150,000 or less.",
        "You’ll only be taxed on the money that comes in and goes out of your business " +
          "during the tax year. This will be easier to calculate and save you time.",
        "Cash basis doesn’t suit all businesses. It can limit claims to some expenses and losses.",
        "Simplified expenses can be used by all self-employed businesses and partnerships which don’t have limited companies as partners.",
        "They’re a way of working out some of your allowable business expenses using flat rates. " +
          "It’s easier than using your actual business costs, which can be difficult to work out.",
        "Flat rates can be used for business costs for",
        bulletPointList.mkString(" "),
        "You’ll need to record",
        bulletPointList2.mkString(" "),
        "You can then apply the flat rate to work out your expenses at the end of the tax year",
        "You can find more information about these schemes on GOV.UK."


      )

      val doc = asDocument(createView())
      val contentDiv = doc
        .getElementById("cash-basis-transcript")
      val texts: List[String] = contentDiv
        .children()
        .asScala
        .toList
        .map(_.text())

      contentList.zip(texts).foreach {
        case (content, element) => element mustBe content
      }

      val bullets: List[String] =
        contentDiv.select("ul.list.list-bullet>li").asScala.toList.map(_.text)
      bulletPointList.zip(bullets).foreach {
        case (content, element) => element mustBe content
      }
    }
  }

}
