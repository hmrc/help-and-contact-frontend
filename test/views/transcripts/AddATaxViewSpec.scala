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

package views.transcripts

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.transcripts.add_a_tax

import scala.collection.JavaConverters._

class AddATaxViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "add.a.tax.transcript"

  def createView = () => inject[add_a_tax].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "Add a tax view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "add-a-tax-transcript"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "You can add more than 40 services to your Business Tax Account. Here’s how you add the four main ones.",
        "Corporation Tax, Self Assessment, PAYE for employers and VAT.",
        "When you log into your Business Tax Account for the first time you’ll see this home page. Any services you add will appear here. For some services, we’ll send you an activation PIN within 10 working days.",
        "Once you put the PIN in, you can start using the service.",
        "Let’s start. Select ‘Corporation Tax’. On the following screen enter your 10-digit Corporation Tax Unique Taxpayer Reference.",
        "Then enter either, your Company Registration Number, sent to you by Companies House, or the postcode of your registered office. If your company is not based in the UK, then select this option.",
        "To add Self Assessment, you’ll need your Unique Taxpayer Reference number. If you don’t have it, you can’t add this service at this time. Instead, you’ll be directed to register for Self Assessment.",
        "Once you receive your Unique Taxpayer Reference letter, log back into your Business Tax Account and choose ‘Yes’. Enter here, your 10-digit Taxpayer Reference number here. Then select if you’re an ‘Individual or sole trader’, ‘Partnership’ or ‘Trust’.",
        "If you’re an individual or sole trader, you’ll be asked to enter your National Insurance number. If you don’t have it, you’ll be asked for your postcode. Next, confirm your identity to get access to Self Assessment right away. But don’t worry if you can’t do this, you can still request an activation pin in the post.",
        "To add PAYE for employers, select this option. Then choose, ‘PAYE for employers’. If you have a PAYE reference number, enter it here as well as the HMRC office number and your Accounts Office reference number.",
        "If you don’t have a PAYE reference number, you’ll be directed to register as an employer. Once you’ve received your reference numbers, log back into your Business Tax Account and add the details.",
        "To add VAT, select the type of VAT service you want. This page tells you what information you need and where to find it. Next, we ask if you have a VAT number. If you haven’t, you’ll need to register for VAT before you continue. When you have it, tell us your 9-digit VAT number here. You’re then asked to enter the date you became VAT registered and the UK postcode for where your business is registered for VAT.",
        "You’ll also need to tell us if you’re currently submitting VAT returns, the total from your latest VAT return and the last month of your latest VAT accounting period.",
        "And that’s it, you can find more information about the Business Tax Account on GOV.UK and in our other videos on YouTube."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}