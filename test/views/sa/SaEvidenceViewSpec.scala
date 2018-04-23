/*
 * Copyright 2018 HM Revenue & Customs
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

import org.jsoup.Jsoup
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.sa.sa_evidence

class SaEvidenceViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "sa.evidence"

  "SaEvidence view" when {
    "the user has an SA enrolment" must{
      def createView = () => sa_evidence(frontendAppConfig, true, "bta-base/more-details")(HtmlFormat.empty)(fakeRequest, messages)
      behave like normalPage(createView, messageKeyPrefix)

      "have a link to the simple assessment subpage" in {
        val doc = asDocument(createView())
        assertLinkById(doc, "more_details", "more Self Assessment details", "bta-base/more-details","HelpSAEvidenceOfIncomeLink:click:EvidenceOfIncome" )
      }
    }
    "the user has no SA enrolment" must{
      def createView = () => sa_evidence(frontendAppConfig, false, "bta-base/more-details")(HtmlFormat.empty)(fakeRequest, messages)
      behave like normalPage(createView, messageKeyPrefix)

      "show the static text" in {
        val doc = asDocument(createView())
        doc.text() must include("You can get evidence of your earnings for the last 4 years with an SA302.")
        doc.text() must include("You can also get a tax year overview for any year. You can get your SA302 in the more Self Assessment details section of your account.")
        doc.text() must include("You might be asked for these documents as evidence of your income, for example if you are applying for a mortgage and you are self-employed.")
      }
    }
  }
}
