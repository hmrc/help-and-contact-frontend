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

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.selfAssessment.register_Deregister

class RegisterDeregisterViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "self_assessment.register_deregister"

  def createView = () => register_Deregister(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

  "RegisterDeregisterSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain correct content" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").first().text() mustBe "Register or deregister for Self Assessment"
    }

    "have tell HM Revenue and Customs (HMRC) if you do not think you need to file tax returns anymore" in {
      val doc = asDocument(createView())
      assertLinkById(doc, "stop-self-assessment",
        "tell HM Revenue and Customs (HMRC) if you do not think you need to file tax returns any more.",
        "https://www.tax.service.gov.uk/business-account/self-assessment/stop",
        "HelpSARegisterDeregisterContentLink:click:StopFilingTaxReturns")
    }
  }
}
