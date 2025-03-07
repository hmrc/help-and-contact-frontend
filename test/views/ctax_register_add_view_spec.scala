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
import views.html.ct.ctax_register_add

class CtaxRegisterAddViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ctax.register.add"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  ctax_register_add.apply(PageType.RegisterAddCT.name)(messages)


  "Register or add Corporation Tax view" must {

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-register-add-corporation-tax"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Register for Corporation Tax with HMRC")
      headings must include("What you need to tell HMRC when registering for Corporation Tax")
      headings must include("Add Corporation Tax to your business tax account")
      headings must include("Register for Corporation Tax with HMRC")
      headings must include("Register for Corporation Tax with HMRC")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("HMRC will tell you the deadline for paying Corporation Tax.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByLinkText(
        doc,
        "registered your company (opens in new tab)",
        "https://www.gov.uk/limited-company-formation/register-your-company"
      )
      assertLinkByLinkText(
        doc,
        "register for Corporation Tax",
        "/business-registration/select-taxes"
      )
      assertLinkByLinkText(
        doc,
        "Check if you’re unsure what counts as being active (opens in new tab)",
        "https://www.gov.uk/guidance/corporation-tax-trading-and-non-trading"
      )
      assertLinkByLinkText(
        doc,
        "Request your company’s Unique Taxpayer Reference",
        "https://www.tax.service.gov.uk/ask-for-copy-of-your-corporation-tax-utr"
      )
      assertLinkByLinkText(
        doc,
        "accounting period (opens in new tab)",
        "https://www.gov.uk/corporation-tax-accounting-period"
      )
      assertLinkByLinkText(
        doc,
        "Company Tax Return (opens in new tab),",
        "https://www.gov.uk/company-tax-returns"
      )
      assertLinkByLinkText(
        doc,
        "Add Corporation Tax to your business tax account",
        "/business-account/add-tax"
      )
      assertLinkByLinkText(
        doc,
        "close the limited company (opens in new tab).",
        "https://www.gov.uk/closing-a-limited-company"
      )
    }
  }
}
