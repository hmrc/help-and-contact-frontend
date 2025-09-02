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
import play.twirl.api.HtmlFormat
import services.ThresholdService
import views.behaviours.ViewBehaviours
import views.html.vat.register_or_deregister

class registerDeregisterForVatViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "register-or-deregister-vat"

  val thresholdService: ThresholdService = inject[ThresholdService]

  def createView: () => HtmlFormat.Appendable = {

    () => register_or_deregister(PageType.RegisterOrDeregisterVAT.route)(messages)
  }

  "Register or Deregister for VAT view" must {

    "contain correct content" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .first()
        .text() mustBe "Register or deregister for VAT"
    }

    "contain the 'register online' link" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "register-for-vat",
        "register for VAT online",
        "https://www.gov.uk/register-for-vat/how-register-for-vat"

      )
    }

    "contain the 'cancel your registration' link" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "cancel-vat-registration",
        "cancel your VAT registration (opens in new tab)",
        "https://www.gov.uk/register-for-vat/cancel-your-registration",
        expectedOpensInNewTab = true
      )
    }
  }

}
