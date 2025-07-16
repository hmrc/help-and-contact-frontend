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

package views.sa

import models.PageType
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.sa.expenses

class ExpensesViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "expenses"

  def createView = () => expenses(PageType.Expenses.name)(messages)

  "Self Assessment Expenses view" must {

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-expenses"
    }

    "contain correct heading" in {
      val doc = asDocument(createView())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Expenses"
    }

    "contain correct content" in {
      val doc = asDocument(createView())
      doc.text() must include("Include all of your allowable expenses for the tax year 6 April to 5 April on your Self Assessment tax return.")

      doc.text() must include("You do not need to send proof of your expenses when you file your tax return.")

      doc.text() must include("You should keep proof and records so that you can show them to HMRC if asked. You need to make sure your records are accurate.")

      doc.text() must include("Simplified expenses cannot be used by limited companies or business partnerships involving a limited company.")

      doc.text() must include("If you’re self-employed, or a business partnership, you can choose to use simplified expenses to work " +
        "out some of your expenses using flat rates instead of working out your actual business costs.")

      doc.text() must include("You can use flat rates for business costs for:")

      doc.text() must include("some vehicles")

      doc.text() must include("working from home")

      doc.text() must include("living in your business premises.")

      doc.text() must include("You can claim allowable business expenses for items including fuel and vehicle insurance.")

      doc.text() must include("You cannot claim for non-business driving, or travel costs, or for fines or travel between home and work.")

      doc.text() must include("You can claim either the actual costs, or a flat rate for business mileage. Once you have chosen one of these" +
        " methods for a particular vehicle, you must continue to use it for that vehicle.")

      doc.text() must include("If you decide to use the actual costs, you will need to know how many miles the vehicle has done over the year. Then work out " +
        "what percentage was for business. You can also claim a capital allowance for the purchase of the vehicle.")

      doc.text() must include("The flat rate covers the whole cost of buying, running and maintaining the vehicle – " +
        "so you cannot claim a capital allowance for its purchase.")

    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "allowable-expenses",
        "Find out more about allowable expenses if you’re self-employed (opens in new tab)",
        "https://www.gov.uk/expenses-if-youre-self-employed",
         expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "find-out-more-simplified",
        "Find out more about simplified expenses (opens in new tab)",
        "https://www.gov.uk/simpler-income-tax-simplified-expenses",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "travel-expenses-sa",
        "Travel expenses if you’re self-employed (opens in new tab)",
        "https://www.gov.uk/expenses-if-youre-self-employed/travel",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "simplified-motoring-expenses",
        "Simplified motoring expenses if you’re self employed (opens in new tab)",
        "https://www.gov.uk/simpler-income-tax-simplified-expenses/vehicles-",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "what-expenses-can-i-include-video",
        "Video – What expenses can I include in my Self Assessment tax return? (opens in new tab)",
        "https://www.youtube.com/watch?v=hspBxF2NVBY",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "claiming-motoring-video",
        "Video - Claiming motoring expenses if you’re self-employed (opens in new tab)",
        "https://www.youtube.com/watch?v=r2txvLXi_Fk",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "claiming-motoring-transcript",
        "Video transcript - Claiming motoring expenses if you’re self-employed",
        "/business-account/help/transcript/calculating-motoring-expenses"
      )

    }

  }

}
