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

package views

import models.PageType
import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.ct.ctax_closing_limited_company

class CtaxClosingLimitedCompanyViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ctax.closinglimitedcompany"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  ctax_closing_limited_company.apply(PageType.ClosingLimitedCompanyCT.name)(messages)


  "Closing a limited company view" must {

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-closing-limited-company"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Dormant companies")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("Your company will still be registered at Companies House.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByLinkText(
        doc,
        "compulsory liquidation (opens in a new tab)",
        "https://www.gov.uk/protecting-company-from-compulsory-liquidation"
      )
      assertLinkByLinkText(
        doc,
        "Find out more about closing a limited company (opens in a new tab)",
        "https://www.gov.uk/closing-a-limited-company"
      )
    }
  }
}
