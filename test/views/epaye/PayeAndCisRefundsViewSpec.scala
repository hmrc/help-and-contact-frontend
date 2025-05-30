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

package views.epaye

import models.PageType
import org.jsoup.nodes.Document
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.epaye.paye_cis_refunds

class PayeAndCisRefundsViewSpec extends ViewBehaviours {

  def createView: () => HtmlFormat.Appendable = () => paye_cis_refunds(PageType.PayeCisRefunds.name)(messages)

  val messageKeyPrefix = "epaye.paye_and_cis_refunds"
  val doc: Document = asDocument(createView())

  "GetCisRefund view" must {

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-paye-and-cis-refunds"
    }

    "have correct h2 headings" in {
      val headings = doc.getElementsByTag("h2").toString
      headings must include("PAYE refunds")
      headings must include("Apply for a refund by post")
      headings must include("CIS refunds")
    }

    "have correct links" in {
      assertLinkById(
        doc,
        "more-about-refunds",
        "More about refunds (opens in new tab)",
        "https://www.gov.uk/payroll-errors/correcting-payments-to-hmrc",
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "how-to-claim-cis",
        "How to claim a CIS refund (opens in new tab)",
        "https://www.gov.uk/guidance/claim-a-refund-of-construction-industry-scheme-deductions-if-youre-a-limited-company",
        expectedOpensInNewTab = true)
    }
  }
}
