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
import views.html.transcripts.your_income_from_property_tax_return

import scala.collection.JavaConverters._

class YourIncomeFromPropertyTaxReturnViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "yourIncomeFromPropertyTaxReturnTranscript"

  def createView = () => inject[your_income_from_property_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "YourSelfEmployedTaxReturn view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "income-from-property-transcript"
    }

    "have correct heading content" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").text mustBe "If I have income from property, how do I fill in my tax return? - video transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "Your tax return must include any income from property in the UK or European Economic Area.",
        "For property income outside of these areas, fill in the foreign income tax return pages instead.",
        "In this section, don’t include income from letting rooms, such as bed and breakfast unless you’re claiming rent a room relief. This counts as trade and goes elsewhere in your tax return.",
        "To begin, tell us what type of property income you had during the year including furnished holiday lettings.",
        "On this page we need to know:",
        "How much property income you received.",
        "If you’re claiming the property income allowance – if so, you can’t claim expenses.",
        "If you’re using traditional accounting – that means you record income and expenses when you invoice customers, or receive a bill.",
        "If you’re not claiming the property income allowance, enter your running costs here. You can include the cost of repairs, but not improvements or upgrades.",
        "If your total annual income is below £85,000, add your total running costs here but don’t include residential finance costs.",
        "Now enter 50% of your residential finance costs – you can enter the other 50% further on.",
        "On page 4 enter what we call adjustments – claims that could reduce your tax bill. For example, if you claim rent a room relief and your income is over the threshold, add the exempt amount of £7,500 – or, if you’re a joint owner, this amount is £3,750.",
        "On page 5 you’ll see your adjusted profit or loss for the year.",
        "If you’re in profit, you must enter losses from an earlier year. We’ll then calculate your taxable profit. This cannot be more than your adjusted profit for the current year.",
        "Enter the remaining 50% of your residential finance costs here. You can also enter any residential finance costs you couldn’t use in the previous tax year. This will help reduce your total tax bill.",
        "On page 6, add any extra information. Then check your figures on the summary screen – you can go back to make corrections.",
        "You can find more information about Self Assessment on GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}