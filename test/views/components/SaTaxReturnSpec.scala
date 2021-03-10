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

package views.components

import org.jsoup.Jsoup
import views.behaviours.ViewBehaviours
import views.html.components.sa_tax_return

class SaTaxReturnSpec extends ViewBehaviours {

  val view = sa_tax_return(frontendAppConfig)(messages)

  "Self Assessment Expenses view" must {

    "contain correct content" in {

      val doc = Jsoup.parse(view.toString)

      doc.text() must include("Self Assessment is a system HM Revenue and Customs (HMRC) uses to collect Income Tax.")

      doc.text() must include("Income Tax is usually deducted automatically from wages, pensions and savings.")

      doc.text() must include("People and businesses with other income must report it by filing a Self Assessment tax return.")

      doc.text() must include("Self Assessment is used by:")

      doc.text() must include("the self-employed")

      doc.text() must include("individuals such as company directors")

      doc.text() must include("partners in partnerships")

      doc.text() must include("trustees")

      doc.text() must include("other similar people")

      doc.text() must include("If you receive a tax return, or a notice to file, you must complete a return and submit it to HMRC.")

      doc.text() must include("If you must file a tax return, you do so after the end of the tax year it applies to.")

      doc.text() must include("The Self Assessment tax year is 6 April to 5 April. For the tax yea" +
        "r 6 April 2019 to 5 April 2020, you must file your return by 31 January 2021.")

      doc.text() must include("You need to register for Self Assessment if:")

      doc.text() must include("you must file a tax return for 2019 to 2020")

      doc.text() must include("and")

      doc.text() must include("you did not file one for the tax year 2018 to 2019")

      doc.text() must include("You should allow up to 20 working days if you have never filed an online tax return before.")

      doc.text() must include("To file your tax return online, you should:")

      doc.text() must include("be registered for Self Assessment")

      doc.text() must include("add Self Assessment to your tax account")

      doc.text() must include("complete and file your tax return by 31 January 2021")

      doc.text() must include("pay what you owe by 31 January 2021")

      doc.text() must include("If you do not register, file or pay on time, you may have to pay a penalty.")

      doc.text() must include("If you have not received a notice to file a Self Assessment tax return, you can")

    }

    "have correct links" in {
      val doc = Jsoup.parse(view.toString)
      assertLinkById(
        doc,
        "who-must-send-link",
        "Who must file a Self Assessment tax return (opens in new tab)",
        "https://www.gov.uk/self-assessment-tax-returns/who-must-send-a-tax-return",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "add-to-sa",
        "add Self Assessment to your tax account",
        "http://localhost:9730/business-account/add-tax/self-assessment/have-sa-utr"
      )
      assertLinkById(
        doc,
        "not-received-sa-return",
        "check anonymously if you need to file a return (opens in new tab).",
        "https://www.gov.uk/check-if-you-need-tax-return",
        expectedOpensInNewTab = true
      )

    }

  }

}
