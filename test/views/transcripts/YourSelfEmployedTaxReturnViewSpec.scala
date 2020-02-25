/*
 * Copyright 2020 HM Revenue & Customs
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

  def createView = () => your_self_employed_tax_return(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

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
        "If you’ve answered yes to this question in section 3 – Tailor your return, you need to tell us about your income from self-employment.",
        "Start by selecting ‘Fill in your return’ – then ‘Enter self-employment details’.",
        "Page one is about your annual turnover. If your trading income’s more than 85 thousand pounds, you may need to register for VAT. If it’s less, select ‘no’ and tick anything on this list that applies to you.",
        "On page two, tell us the name of your business and what you do. It’s important to let us know if you started or stopped being self-employed during the year – enter the dates here.",
        "Add your accounting details on page three, including the date your accounting year ends. We recommend using the fifth of April, as it’s the end of the tax year.",
        "Also tell us if you’re using cash basis – if you’re not sure what this means, use the question mark to find out more. Or watch our YouTube video ‘Cash basis and simplified expenses’.",
        "On page four, enter the figures for your turnover and allowable expenses. You can claim the one thousand pound trading allowance here – but this means you can’t deduct any other expenses.",
        "When your figures are in, your calculations will appear at the bottom of the screen.",
        "Page five is for Capital Allowances. If you’re using cash basis, you can only claim for business cars.",
        "Page six is for what we call adjustments. These include the value of any goods or services for personal use, other business income, and any losses brought forward from an earlier year.",
        "Page seven asks about any losses from the current tax year.",
        "Page eight is for tax deducted – only fill in this box if you’re in the Construction Industry Scheme, to show your contractor deductions.",
        "On page nine enter your Class 4 National Insurance contributions.",
        "At the end, you’ll see a summary page so you can check everything.",
        "You’ll find more information about completing a Self Assessment tax return on GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

    }
  }
}