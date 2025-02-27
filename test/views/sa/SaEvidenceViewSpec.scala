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

package views.sa

import models.PageType
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.sa.get_evidence_of_income

class SaEvidenceViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "sa302.evidence"

  "SaEvidence view" when {
    "the user has an SA enrolment" must{
      def createView(hasUtr: Boolean = false) = () =>
        get_evidence_of_income(PageType.GetEvidenceOfIncome.name, frontendAppConfig, hasUtr)(Some(HtmlFormat.empty))(fakeRequest, messages)

      "have a link to find out more page" in {
        val doc = asDocument(createView()())
        assertLinkById(
          doc,
          "getting-evidence",
          "Find out more about getting evidence of your income (opens in new tab)",
          "https://www.gov.uk/sa302-tax-calculation",
          expectedOpensInNewTab = true
        )
      }
      "have a link to find your SA302" in {
        val doc = asDocument(createView(true)())
        assertLinkById(
          doc,
          "find-sa302",
          "Find your SA302",
          "http://localhost:9020/business-account/self-assessment-govuk"
        )
      }
    }
    "the user has no SA enrolment" must{
      def createView = () =>
        get_evidence_of_income(PageType.GetEvidenceOfIncome.name, frontendAppConfig, hasSAenrolment = false)(Some(HtmlFormat.empty))(fakeRequest, messages)

      "contain heading ID" in {
        val doc = asDocument(createView())
        doc.getElementsByTag("h1").attr("id") mustBe "page-title-get-evidence-of-income"
      }

      "show the static text" in {
        val doc = asDocument(createView())
        doc.text() must include("If you complete your return online you can:")
        doc.text() must include("get evidence of your earnings (SA302) for the last 4 years that you filed online")
        doc.text() must include("get a tax year overview for any year")
        doc.text() must include("print your calculation and tax year overview")
        doc.text() must include("You might be asked for these documents as evidence of your income, for example " +
          "if you are applying for a mortgage and you are self-employed.")
        doc.text() must include("Check that your mortgage provider accepts documents you have printed yourself.")
        doc.text() must include("You cannot print your documents until up to 72 hours after you have sent your tax return.")
      }
    }
  }
}
