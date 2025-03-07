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
import views.html.epaye.paye_stop_employer

class PayeStopEmployerViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "paye.stop.employer"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(): () => HtmlFormat.Appendable =
    () =>  paye_stop_employer.apply(PageType.PayeStopEmployer.name)(messages)


  "Paye Stop Employer View" must {

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-stop-being-an-employer"
    }

    "have correct h2 headings" in {
      val doc      = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("If you temporarily stop employing staff")
      headings must include("Mergers and successions")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkByLinkText(
        doc,
        "your business merges or changes ownership (opens in a new tab).",
        "https://www.gov.uk/guidance/payroll-what-to-do-if-your-business-merges-or-changes-ownership"
      )
      assertLinkByLinkText(
        doc,
        "stopping being an employer (opens in a new tab).",
        "https://www.gov.uk/stop-employing-staff"
      )
    }
  }
}
