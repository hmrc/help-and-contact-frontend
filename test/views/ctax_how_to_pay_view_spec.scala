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

package views.ct

import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.twirl.api.{Html, HtmlFormat}
import models.{PageType, SaUtr}
import views.behaviours.ViewBehaviours
import views.html.{ctax_how_to_pay, help_and_contact}

class CtaxHowToPayViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ctax.howtopay"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  ctax_how_to_pay.apply()(messages)


  "HowToPayCorporationTax view" must {
   // behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "ctax-how-to-pay"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Deadlines")
      headings must include("If your taxable profits are up to £1.5 million")
      headings must include("If your taxable profits are more than £1.5 million")
      headings must include("Ways to pay")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("The deadline for your payment will depend on your taxable profits.")
      doc.text() must include(
        "You must pay your Corporation Tax 9 months and 1 day after the end of your accounting period. " +
          "Your accounting period is usually your financial year, but you may have 2 accounting periods in "
      )
      doc.text() must include("You must ")
      doc.text() must include(" if you do not pay on time. They will ")
      doc.text() must include(" if you pay your tax early.")
      doc.text() must include("You can no longer pay at the Post Office. You cannot pay Corporation Tax by post.")
      doc.text() must include("The time you need to allow depends on how you pay.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByDocId(
        doc,
        "ct-how-to-pay-video",
        "Video - Paying your Corporation Tax bill (opens in new tab)",
        "https://www.youtube.com/watch?v=_eTj5_S9nrA",
        "",
        expectedOpensInNewTab = true
      )
      assertLinkByDocId(
        doc,
        "ct-how-to-pay-video-transcript",
        "Paying your Corporation Tax bill - video transcript",
        "/business-account/help/transcript/how-to-pay-corporation-tax",
        "link - click:How to pay your Corporation Tax:Video transcript",
        exactUrl = false
      )
    }
  }
}
