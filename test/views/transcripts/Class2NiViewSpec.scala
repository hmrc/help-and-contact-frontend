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
import views.html.transcripts.class_2_ni

import scala.collection.JavaConverters._

class Class2NiViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "class_2_ni"

  def createView: () => Html =
    () =>
      inject[class_2_ni].apply(frontendAppConfig)(Some(HtmlFormat.empty))(
        fakeRequest,
        messages
    )

  "RegisteringForSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .attr("id") mustBe "class-2-ni-transcript"
    }

    "have correct content" in {
      val bulletPointList = List(
        "you may not be registered correctly for self-employment, so you’ll need to register on GOV.UK or",
        "the service may be unavailable. When it’s available select the ‘Recalculate’ button to show the amount due."
      )

        val bulletPointList2 = List(
          "you’re not registered correctly for self-employment",
          "you’ve already paid the maximum National Insurance",
          "you received certain benefits meaning no Class 2 is due",
          "you were abroad and pay Class 2 differently"
        )

      val contentList: List[String] = List(
        "If you’re self-employed or in a partnership, you pay Class 2 National Insurance to qualify for State Pension and other benefits. " +
          "Most people pay this in their Self Assessment payment – by 31 January, following the end of the tax year.",
        "You must pay if your profits are at, or above, the Small Profits Threshold – you can find the current rates on GOV.UK.",
        "You don’t have to pay if your profits are under the threshold – but this can affect your State Pension and other benefits. " +
          "So, you may decide to pay voluntary contributions.",
        "When you’re self-employed, to pay Class 2 National Insurance, you need to register for Self Assessment. You also need to do this " +
          "when your annual turnover is below a thousand pounds and you want to pay voluntary contributions.",
        "On your tax return, select self-employed or in a partnership – this is the section where your Class 2 contributions are included.",
        "Your return shows the amount due, based on the number of weeks in the tax year you were self-employed on your National Insurance records.",
        "If the amount shows ‘Not available, check help’:",
        bulletPointList.mkString(" "),
        "If the amount shows zero, this may be because:",
        bulletPointList2.mkString(" "),
        "If you’re not sure, contact the National Insurance helpline.\n",
        "If you started or stopped self-employment during the year and think the amount is incorrect, select the ‘Update your" +
          " self-employment details’ link to update your record, then select ‘Recalculate’.",
        "To make voluntary Class 2 payments, select ‘Yes’, otherwise select ‘No’.",
        "Your full calculation shows how much tax and National Insurance you owe.",
        "You can find more information about Self Assessment on GOV.UK.",


      )

      val doc = asDocument(createView())
      val contentDiv = doc
        .getElementById("class-2-ni-transcript")
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
