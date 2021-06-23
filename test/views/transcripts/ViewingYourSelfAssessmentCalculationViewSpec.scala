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
import views.html.transcripts.viewing_your_self_assessment_calculation

import scala.collection.JavaConverters._

class ViewingYourSelfAssessmentCalculationViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "viewingYourCalculationTranscript"

  def createView = () => inject[viewing_your_self_assessment_calculation].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "ViewingYourCalculation view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "viewing-your-sa-calculation-transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())
      val bullets = doc.getElementsByTag("article").first().getElementsByTag("li").asScala.toList.map(_.text())

      val bulletListPara2 = List("Income Tax", "Class 4 National Insurance", "and Class 2 National Insurance contributions")
      val bulletListPara8 = List("income", "personal allowance", "tax due", "Class 4 National Insurance", "Class 2 National Insurance", "and any payments due")
      val bulletsList = bulletListPara2 ++ bulletListPara8

      val contentList = List(
        "When you use our Self Assessment online tax return, it automatically works out how much you’ll need to pay us.",
        "After adding your figures and checking everything is correct, you can view your calculation." +
          " This shows the amount you’re due to pay for this tax return. It will include:",
        "if they’re due.",
        "If the amount is £1,000 or more, you have to make a ‘payment on account’.",
        "A payment on account is made twice a year on 31 January and 31 July to help you spread the cost of each year’s tax.",
        "Each payment is usually half of your previous tax bill and as you pay in advance they " +
          "are taken off the amount you’re due to pay the following year.",
        "If you’d like to see how the figures have been worked out in more detail, select ‘View and print your full calculation’.",
        "This shows your:",
        "You can print a copy of this for your own records. And remember, these estimated payments don’t include any payments you’ve already made.",
        "You can find more information about Self Assessment on GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

      bulletsList.zipAll(bullets, "", "").foreach {
        case (expected, actual) => actual mustBe expected
      }
    }
  }
}


