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
import views.html.epaye.epaye_get_started

class EpayeGetStartedViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "epaye"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  epaye_get_started.apply(PageType.GetStarted.name)(messages)


  "Epaye Get Started view" must {

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-get-started-paye"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("1. Payroll software")
      headings must include("2. Paying your employees")
      headings must include("3. Paying HMRC")
      headings must include("Further resources")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("You cannot pay your employees until you’ve installed payroll software.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByLinkText(
        doc,
        "running your payroll (opens in new tab)",
        "https://www.gov.uk/running-payroll"
      )
      assertLinkByLinkText(
        doc,
        "when you can submit a late FPS (opens in a new tab).",
        "https://www.gov.uk/running-payroll/fps-after-payday"
      )
      assertLinkById(
        doc,
        "when-and-how-to-pay-epaye-video",
        "Video – When and how to pay PAYE (opens in new tab)",
        "https://www.youtube.com/watch?v=PnjlHOuCnK8",
        expectedOpensInNewTab = true
      )
    }
  }
}
