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
import views.html.transcripts.new_tailor_tax_return

import scala.collection.JavaConverters._

class NewTailorYourTaxReturnSpec extends ViewBehaviours {

  val messageKeyPrefix = "sa.new_tailor_tax_return_transcript"

  def createView = () => inject[new_tailor_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "NewTailorYourTaxReturn view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "new-tailor-your-tax-transcript-heading"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/",
        expectedOpensInNewTab= true)
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "When you’re filing your online tax return, you’ll find a section called ‘Tailor your return’.",
        "It’s three pages of questions where you answer ‘Yes’ or ‘No’, which means you only get " +
          "the sections you need to complete. If you’re not sure about a question, click on the ‘Help about’ link for guidance.",
        "Page one asks if you’ve had income as an employee, from self-employment or from a partnership.",
        "You must answer ‘Yes’ or ‘No’ to all the questions. If you choose ‘Yes’, you may be asked for " +
          "more information; for example, if you say yes to being employed, it asks how many employers you had, and their names.",
        "Using ‘Save and continue’ takes you to the next page. This saves your answers so far, you can then stop and come back later.",
        "Page two asks about other kinds of income, such as interest from bank and savings accounts, dividends from shares, and pensions.",
        "Please note the question about losses on other taxable income refers to income such as casual earnings or commission. " +
          "It’s not for losses that belong elsewhere in your return – for example, losses from self-employment go in the self-employment section. " +
          "If in doubt, use the ‘Help about’ link for further guidance.",
        "The third page is mainly about tax reliefs, including pension contributions, charitable gifts, Married Couple’s Allowance and Marriage Allowance.",
        "This is for tax you’ve paid in the tax year covered by this online return, and which has " +
          "already been refunded by us or Jobcentre Plus. It’s not for refunds that came from an earlier year, or from an employer.",
        "The next section is Fill in your return. Based on your answers in Tailor your return, it shows you the sections you need to complete next.",
        "Remember, your Self Assessment tax return covers the tax year that ended on the fifth of April and is due by the thirty first " +
          "of January the following year. Any tax you owe must be paid by the thirty first of January.",
        "You can file anytime, just don’t leave it until the last minute.",
        "You can find out more about Self Assessment on GOV.UK and watch our other helpful videos and webinars on YouTube."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}

