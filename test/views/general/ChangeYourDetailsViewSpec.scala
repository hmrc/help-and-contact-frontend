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

package views.general

import models.PageType
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.general.change_contact_and_account_details

class ChangeYourDetailsViewSpec extends ViewBehaviours {

  def createView = () => change_contact_and_account_details(PageType.ChangeContactAndAccountDetails.name, frontendAppConfig)(messages)

  "change your details view" must {

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-change-contact-and-account-details"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "change-your-details-managing-hmrc-details",
        "managing your HMRC sign in details",
        "http://localhost:9020/user-profile-redirect-frontend/profile-management"
      )
      assertLinkById(
        doc,
        "change-your-details-business-account-details",
        "go to your business tax account details",
        "http://localhost:9020/business-account/manage-account/account-details"
      )
      assertLinkById(
        doc,
        "change-your-details-standard-user",
        "manage the HMRC sign in details for a standard user (opens in new tab)",
        "http://localhost:9020/tax-and-scheme-management/services",
         expectedOpensInNewTab = true

      )
      println("0")
      assertLinkById(
        doc,
        "change-your-details-more-help",
        "Get help with HMRC services if you have problems signing in (opens in new tab)",
        "https://www.gov.uk/log-in-register-hmrc-online-services/problems-signing-in",
        expectedOpensInNewTab = true
      )
    }
  }

}
