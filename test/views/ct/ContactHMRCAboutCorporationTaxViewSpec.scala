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

package views.ct

import models.SaUtr
import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.ct.contact_hmrc_about_ct

class ContactHMRCAboutCorporationTaxViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ct.contact_hmrc"

  def fakeServiceInfoRequest(saUtr: Option[SaUtr] = None) = {
    ServiceInfoRequest(AuthenticatedRequest(fakeRequest, saUtr, None), Some(HtmlFormat.empty))
  }

  def createView(saUtr: Option[SaUtr] = None) = () => inject[contact_hmrc_about_ct].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeServiceInfoRequest(saUtr), messages)

  "Contact HMRC about corporation tax view" must {
    behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "contact-hmrc-ct"
    }

    "have correct h2 headings" in {
      val doc = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Phone")
      headings must include("Post")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("Call HMRC for help with general Corporation Tax enquiries.")
      doc.text() must include("You’ll need your 10-digit Unique Tax Reference when you call." +
        " You can find this on letters from HMRC and in")
      doc.text() must include("Telephone:")
      doc.text() must include("0300 200 3410")
      doc.text() must include("Outside UK: ")
      doc.text() must include("+44 151 268 0571")
      doc.text() must include("Opening times:")
      doc.text() must include("Monday to Friday: 8am to 6pm")
      doc.text() must include("Closed weekends.")
      doc.text() must include("Best time to call:")
      doc.text() must include("Phone lines may be less busy between 8am to 9am and 5pm to 6pm.")
      doc.text() must include("Write to HMRC for help with general Corporation Tax enquiries.")
      doc.text() must include("You do not need to include a street name, city name or PO box when writing to this address.")
      doc.text() must include("Couriers should use a")
      doc.text() must include("Corporation Tax Services")
      doc.text() must include("HM Revenue and Customs")
      doc.text() must include("BX9 1AX")
      doc.text() must include("United Kingdom")
      doc.text() must include("If you’re replying to a letter you’ve received about your Corporation Tax," +
        " you should use the address on that letter.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "your-online-services-account",
        "your business tax account",
        "http://localhost:9020/business-account",
        )
      assertLinkById(
        doc,
        "call-charges",
        "Find out about call charges",
        "https://www.gov.uk/call-charges",

        expectedOpensInNewTab = false)
      assertLinkById(
        doc,
        "courier-address",
        "use a different address",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/couriers",

        expectedOpensInNewTab = false)
    }
  }
}
