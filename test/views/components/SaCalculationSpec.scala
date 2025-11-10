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

package views.components

import org.jsoup.Jsoup
import views.behaviours.ViewBehaviours
import views.html.components.sa_calculation

class SaCalculationSpec extends ViewBehaviours {

  val view = sa_calculation(appConfig = frontendAppConfig)(messages)

  "Self Assessment Expenses view" must {

    "contain correct content" in {

      val doc = Jsoup.parse(view.toString)

      doc.text() must include("HMRC’s online tax return automatically works out how much you need to pay. " +
        "You fill in your figures and once you have checked that everything is correct, you can view your calculation.")

      doc.text() must include("The calculation shows:")

      doc.text() must include("how your tax bill has been worked out")

      doc.text() must include("your income")

      doc.text() must include("your personal allowance")

      doc.text() must include("the tax due")

      doc.text() must include("You can print a copy of the calculation for your records.")

    }

    "have correct links" in {
      val doc = Jsoup.parse(view.toString)
      assertLinkById(
        doc,
        "any-class-2-4",
        "any class 2 and class 4 National Insurance contributions (opens in new tab)",
        "https://www.gov.uk/self-employed-national-insurance-rates",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "viewing-sa-tax-calculation-video",
        "Video - Viewing your Self Assessment tax return calculation (opens in new tab)",
        "https://www.youtube.com/watch?v=-TRf6nKkNKI",

        expectedOpensInNewTab = true
      )


    }

  }

}
