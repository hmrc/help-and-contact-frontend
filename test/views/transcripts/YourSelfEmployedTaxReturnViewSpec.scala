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
import views.html.transcripts.your_self_employed_tax_return

import scala.collection.JavaConverters._

class YourSelfEmployedTaxReturnViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "yourSelfEmployedTaxReturnTranscript"

  def createView = () => inject[your_self_employed_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "YourSelfEmployedTaxReturn view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "your-self-employed-tax-return-transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "This is one of a series of videos about online Self Assessment tax returns.",
        "You’ve tailored your return. Now tell HMRC about your self-employed income.",
        "The first question is about your annual turnover.",
        "If you didn’t trade for a full year, but had you, and your income would’ve been over £85,000, you answer “yes”.",
        "For example:",
        "If your business income over 6 months was £50,000, then for 12 months it would’ve been £100,000.",
        "If your trading income is over £85,000 then you may need to register for VAT.",
        "You’ll also need to give detailed information, such as listing expenses.",
        "We’re choosing ‘no’.",
        "Statements appear about your self-employment. Choose those that apply. " +
          "Many people will select ‘None of these apply’.",
        "Your business description goes on page 2.",
        "If you started or ceased self-employment during the year, " +
          "it’s important to enter the dates so HMRC can update your records.",
        "This next page is for your accounting details. Enter your end of year date. " +
          "When you start self-employment we recommend you use fifth April as this is the end of the tax year.",
        "Consider cash basis. If you answer ‘Yes’, you may not be able to make claims for some expenses, " +
          "capital allowances and loss relief.",
        "Use the question mark for guidance or search for cash basis on GOV.UK.",
        "On this page, show your turnover and allowable expenses.",
        "This is where you can claim the new £1,000 trading allowance. " +
          "If you use this allowance you can’t deduct any other expenses or capital expenses.",
        "If you don’t use the trading allowance and your income is below the VAT threshold, " +
          "just add together all the allowable expenses and enter a single figure, " +
          "or you can enter them separately.",
        "The calculations are done automatically.",
        "You’re then taken to the Capital Allowances page.",
        "With cash basis, you can only claim capital allowances on business cars.",
        "Other tax adjustments are on the next page. Include the value of any goods or services you’ve had for your own use, " +
          "any losses brought forward from an earlier year and any other business income.",
        "The next page is for losses. There’s a link to a worksheet and helpsheet 227 with more guidance. " +
          "These aren’t sent to HMRC.",
        "You only need to complete the tax deducted page if you’re in the construction industry scheme " +
          "to show contractor deductions.",
        "Only answer ‘Yes’ if you’re exempt from Class 4 National Insurance contributions. The few exemptions are listed.",
        "There’s a screen for other information, then check your entries on this summary page.",
        "If you’ve stopped self-employment tell HMRC.",
        "You’ll find more help and support on GOV.UK.",
        "Webinars and other videos about Self Assessment are available from HMRC.",
        "Thanks for watching."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

    }
  }
}