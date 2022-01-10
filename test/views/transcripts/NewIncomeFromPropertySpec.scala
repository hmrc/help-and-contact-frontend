/*
 * Copyright 2022 HM Revenue & Customs
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
import views.html.transcripts.new_income_from_property

import scala.collection.JavaConverters._

class NewIncomeFromPropertySpec extends ViewBehaviours {

  val messageKeyPrefix = "new.incomeFromPropertyTaxReturnTranscript"

  def createView: () => Html =
    () =>
      inject[new_income_from_property].apply(frontendAppConfig)(Some(HtmlFormat.empty))(
        fakeRequest,
        messages
      )

  "IncomeFromPropertyTax view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .attr("id") mustBe "income-from-property-transcript"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/",
        "link - click:Transcript: GOV.UK home")
    }

    "have correct content" in {

      val contentList: List[String] = List(
        "Your tax return must include any income from property that’s in the UK or European Economic Area.",
        "For property income outside of these areas, fill in the foreign income tax return pages instead.",
        "In this section, don’t include income from letting rooms, such as bed and breakfast unless you’re claiming rent a room relief." +
          " This counts as trade and goes elsewhere in your tax return.",
        "To begin, tell us what type of property income you had during the year including furnished holiday lettings.",
       "On this page tell us how much property income you received. Include any coronavirus " +
         "support scheme payments. Use the ‘help about’ link for further guidance.",
        "We need to know if you’re claiming the property income allowance – if you are, then you can’t claim expenses.",
        "You also need to tell us if you’re using traditional accounting – " +
          "that means you record income and expenses when you invoice customers or receive a bill.",
        "If you’re not claiming the property income allowance, enter your running costs here. You can include the cost of repairs, " +
          "but not improvements or upgrades. Don’t include any residential finance costs on this page.",
        "If your total annual income is below 85 thousand pounds, add your total running costs here but don’t include residential finance costs.",
        "Next, enter 25 percent of your residential finance costs – you can enter the other 75 percent further on.",
        "On page four enter what we call adjustments – claims that could reduce your tax bill. For example," +
          " if you claim rent a room relief and your income is over the threshold, add the exempt amount of £7,500 – or, £3,750, if you’re a joint owner.",
        "On page five you’ll see your adjusted profit or loss for the year.",
        "If you’re in profit, you must enter losses from an earlier year. This amount cannot be more than your adjusted profit for the current year." +
          " We’ll then calculate your taxable profit.",
        "Enter your total residential finance costs for this year here. This will help reduce your total tax bill. You can also enter any residential " +
          "finance costs you couldn’t use in the previous tax year. Use the ‘Help about’ link to work out the amount to enter.",
        "Finally, on page six, add any extra information. Then check your figures on the summary screen – you can go back to make corrections.",
        "You can find more information about Self Assessment on GOV.UK."
      )

      val doc = asDocument(createView())
      val contentDiv = doc
        .getElementById("incomeFromPropertyTranscript-content")
      val texts: List[String] = contentDiv
        .children()
        .asScala
        .toList
        .map(_.text())

      contentList.zip(texts).foreach {
        case (content, element) => element mustBe content
      }

    }
  }

}

