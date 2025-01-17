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

package views.vat

import models.PageType
import org.jsoup.Jsoup
import views.behaviours.ViewBehaviours
import views.html.vat.correcting_errors_on_returns

class CorrectingErrorsOnReturns extends ViewBehaviours {

  val view = correcting_errors_on_returns(PageType.CorrectingErrorsOnReturns.name)(messages)

  "VAT Correcting Errors On Returns view" must {

    "contain correct content" in {

      val doc = Jsoup.parse(view.toString)

      doc.text() must include("You can correct errors in returns for the preceding 4 years, if the net value of the errors is either:")
      doc.text() must include("£10,000 or less")
      doc.text() must include("between £10,000 and £50,000 but less than 1% of the total value of your sales")
      doc.text() must include("For these kinds of errors, make an adjustment or correction in your next return.")
      doc.text() must include("You must tell HMRC separately about net errors that are:")
      doc.text() must include("over £50,000")
      doc.text() must include("over £10,000 if they exceed 1% of the total value of sales")
      doc.text() must include("deliberate errors")
    }

    "have correct links" in {
      val doc = Jsoup.parse(view.toString)
      assertLinkById(
        doc,
        "correcting-vat-errors-link",
        "correcting errors in your VAT return (opens in new tab)",
        "https://www.gov.uk/submit-vat-return/correct-errors-in-your-vat-return",
        expectedOpensInNewTab = true
      )
    }

  }

}
