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
import views.html.vat.{correcting_errors_on_returns, how_to_pay_vat_and_deadlines}

class HowToPayVatAndDeadlines extends ViewBehaviours {

  val view = how_to_pay_vat_and_deadlines(PageType.HowToPayVatAndDeadlines.name)(messages)

  "How to pay VAT and deadlines view" must {

    "contain correct content" in {

      val doc = Jsoup.parse(view.toString)

      doc.text() must include("The deadline for submitting the return online and paying HMRC are usually the same - 1 calendar month and 7 days after the end of an accounting period.")
      doc.text() must include("You need to allow time for the payment to reach HMRC’s account.")
      doc.text() must include("VAT refunds on goods imported from the EU")
      doc.text() must include("You can get a refund of VAT paid in another EU country (opens in new tab) if you are registered for VAT in the UK, but:")
      doc.text() must include("what you can reclaim depends on the other EU country’s rules for claiming VAT")
      doc.text() must include("the rules for claiming input tax (opens in new tab) are slightly different in each EU country")
      doc.text() must include("each EU country has set a minimum amount that can be refunded")
      doc.text() must include("if your business makes both taxable and exempt supplies, you may not be able to reclaim all of the VAT you have been charged")
    }

    "have correct links" in {
      val doc = Jsoup.parse(view.toString)
      assertLinkById(
        doc,
        "how-to-get-help-vat-video",
        "Video - How to get help when you can’t pay your tax bill (opens in a new tab)",
        "https://www.youtube.com/watch?v=oXzjkDkPTrM&t=6s",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "get-a-refund-of-vat-link",
        "get a refund of VAT paid in another EU country (opens in new tab)",
        "https://www.gov.uk/guidance/vat-refunds-for-uk-businesses-buying-from-other-eu-countries" ,
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "rules-for-claiming-link",
        "rules for claiming input tax (opens in new tab)",
        "https://www.gov.uk/guidance/vat-guide-notice-700#input-tax-introduction-and-general-rules" ,
        expectedOpensInNewTab = true
      )
    }

  }

}
