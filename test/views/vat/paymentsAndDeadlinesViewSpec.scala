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

///*
// * Copyright 2024 HM Revenue & Customs
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package views.vat
//
//import play.twirl.api.HtmlFormat
//import views.behaviours.ViewBehaviours
//import views.html.vat.payments_and_deadlines
//
//class paymentsAndDeadlinesViewSpec extends ViewBehaviours {
//
//  val messageKeyPrefix = "vat.payments_and_deadlines"
//
//  def createView = () => inject[payments_and_deadlines].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
//
//  "VAT Payments and Deadlines view" must {
//
//    behave like normalPage(createView, messageKeyPrefix)
//
//    "contain heading ID" in {
//      val doc = asDocument(createView())
//      doc.getElementsByTag("h1").attr("id") mustBe "payments-and-dealdines"
//    }
//
//    "contain correct content" in {
//      val doc = asDocument(createView())
//      doc.getElementsByTag("h1").first().text() mustBe "How to pay VAT and deadlines"
//    }
//
//    "contain the 'online or telephone banking' link" in {
//      val doc = asDocument(createView())
//      assertLinkById(
//        doc,
//        "how-to-pay-vat",
//        "Read detailed information about how to pay your VAT bill",
//        "https://www.gov.uk/pay-vat/bank-details",
//
//        expectedOpensInNewTab = false)
//    }
//
//    "contain the 'get a refund of VAT paid in another EU country' link" in {
//      val doc = asDocument(createView())
//      assertLinkById(doc,
//        "get-eu-vat-refund",
//        "get a refund of VAT paid in another EU country",
//        "https://www.gov.uk/guidance/vat-refunds-for-uk-businesses-buying-from-other-eu-countries",
//
//        expectedOpensInNewTab = false)
//    }
//
//    "contain the 'input tax' link" in {
//      val doc = asDocument(createView())
//      assertLinkById(doc,
//        "input-tax",
//        "rules for claiming input tax",
//        "https://www.gov.uk/government/publications/vat-notice-700-the-vat-guide/vat-notice-700-the-vat-guide#input-tax-introduction-and-general-rules",
//
//        expectedOpensInNewTab = false)
//    }
//
//  }
//
//}
