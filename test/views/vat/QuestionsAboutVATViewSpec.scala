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

package views.vat

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.vat.questions_about_vat

class QuestionsAboutVATViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "vat.questions_about_vat"

  def createView = () => questions_about_vat(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

  "QuestionsAboutVAT view" must {
    behave like normalPage(createView, messageKeyPrefix)
    val doc = asDocument(createView())

    "contain correct content" in {
      doc.getElementsByTag("h1").first().text() mustBe "Contact HMRC about VAT"
    }

    "contain the 'make a VAT query online' link" in {
      val doc = asDocument(createView())
      assertLinkById(doc,
        linkId = "make-a-vat-enquiry-online",
        expectedText = "make a VAT enquiry online",
        expectedUrl="http://localhost:8080/portal/shortforms/form/VATGenEnq?dept-name=&sub-dept-name=&location=47&lang=eng",
        expectedGAEvent = "HelpVatContactHMRCContentLink:click:MakeEnquiryOnline")
    }

    "contain the 'call us' link" in {
      val doc = asDocument(createView())
      assertLinkById(doc,
        linkId = "contact-hmrc-about-vat",
        expectedText = "call us",
        expectedUrl="https://www.gov.uk/government/organisations/hm-revenue-customs/contact/vat-enquiries",
        expectedGAEvent = "HelpVatContactHMRCContentLink:click:CallUs")
    }

  }
}
