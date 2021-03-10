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

import views.behaviours.ViewBehaviours
import views.html.covid

class covidSectionSpec extends ViewBehaviours {

  val messageKeyPrefix = "help_and_contact"

  def createView =
    () =>
      covid(frontendAppConfig)(
        fakeRequest,
        messages
      )

  "Help and contact view" must {

    "contain heading" in {
      val doc = asDocument(createView())
      doc.getElementById("covid-help").html() mustBe "Coronavirus (COVID-19) support"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "covid-digital-assistant-for-help-and-support",
        "Digital assistant for help and support (opens in new tab)",
        "https://www.tax.service.gov.uk/ask-hmrc/virtual-assistant/support-for-coronavirus",
          expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "covid-financial-support-for-businesses",
        "Financial support for businesses (opens in new tab)",
        "https://www.gov.uk/government/collections/financial-support-for-businesses-during-coronavirus-covid-19#business-support-grant-funds",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "covid-debt-management-for-tax-bills",
        "Debt management for tax bills: contact HMRC (opens in new tab)",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/coronavirus-covid-19-helpline",
        expectedOpensInNewTab = true
      )
    }

  }

}
