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

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/",
        expectedOpensInNewTab = true)
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())
      val bullets = doc.getElementsByTag("article").first().getElementsByTag("li").asScala.toList.map(_.text())

      val contentList = List(
        "When you use our Self Assessment online tax return, it automatically works out how much you’ll need to pay us.",
        "After adding your figures, and checking everything is correct, you can view your calculation.",
        "This summary shows the amount you’re due to pay for this tax return based on the figures you’ve entered.",
        "It will include Income Tax, Class 4 National Insurance and voluntary Class 2 National Insurance contributions, if they’re due.",
        "Any Capital Gains Tax and Student Loan repayments due, will also be included.",
        "If the amount of Income Tax and Class 4 National Insurance is £1,000 or more, you have to make a payment on account.",
        "A payment on account is made twice a year, on the 31st of January and the 31st of July, to help you spread the cost of each year’s tax.",
        "Each payment is half of your last Income Tax and Class 4 National Insurance bill." +
          " These payments are taken off the amount you’re due to pay the following year.",
        "Select ‘Save and continue’ at the bottom of the summary page to see how the figures have been worked out in more detail. This shows your income, personal"+
        " allowance, income tax due and any Class 4 National Insurance due. Any voluntary Class 2 National Insurance will also show here.",
        "The total of any payments due are shown further down the page.",
        "Remember, these estimated payments don’t include any payments you’ve already made. You can print a copy of this for your own records.",
        "You can find more information about Self Assessment on GOV.UK and in our other videos on YouTube."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}


