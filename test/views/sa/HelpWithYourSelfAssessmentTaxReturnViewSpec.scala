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

package views.sa

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import models.{SaUtr, SaUtrGenerator}
import views.html.sa.help_with_your_self_assessment_tax_return
import collection.JavaConverters._

class HelpWithYourSelfAssessmentTaxReturnViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "helpWithYourSelfAssessmentTaxReturn"

  def createView(utr: Option[SaUtr] = None) = () => inject[help_with_your_self_assessment_tax_return].apply(frontendAppConfig, utr)(HtmlFormat.empty)(fakeRequest, messages)

  "SelfAssessmentTaxReturnCheck view" must {
    behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "help-with-your-sa-tax-return"
    }

    "have correct h2 headings" in {
      val listOfHeadings: List[String] = List(
        "Self Assessment tax returns",
        "Registering for Self Assessment",
        "Why you must complete and file a Self Assessment tax return",
        "If you do not need to complete and file a Self Assessment tax return",
        "Your first Self Assessment tax return",
        "Help with completing and filing your tax return",
        "How to tailor your return",
        "If you are self-employed",
        "If you have income from property",
        "Viewing your Self Assessment calculation",
        "More help with Self Assessment",
        "Basic record keeping when you’re self-employed",
        "More help with record keeping"
      )
      val doc = asDocument(createView()())
      val headings = doc.getElementsByTag("article").first.getElementsByTag("h2").asScala.toList.map(_.text())

      headings mustBe listOfHeadings
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("You will usually be sent a tax return or a letter about a tax return if you are registered as self-employed.")

      doc.text() must include("You must file your tax return even if you have no tax to pay. " +
        "If you do not file it by the deadline, you may have to pay a penalty.")

      doc.text() must include("You must also tell us as soon as you have stopped being self-employed.")

      doc.text() must include("A tax return is a form on which you:")

      doc.text() must include("HMRC may issue a tax return to you each tax year." +
        " The Self Assessment tax year runs from 6 April one year to 5 April the next year.")

      doc.text() must include("If you receive a tax return, or a notice to file (SA316), the law says you must complete it.")

      doc.text() must include("We use the information on your tax return to work out your tax bill or whether you are due a tax refund.")

      doc.text() must include("If this is your first tax return, you must register for Self Assessment before you can complete and file your return.")

    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "find-out-more-link",
        "Find out more about registering for Self Assessment online",
        "/business-account/help/self-assessment/register-or-stopping"
      )
      assertLinkById(
        doc,
        "why-i-have-been-sent-tax-video",
        "Video - Why have I been sent a tax return? (opens in new tab)",
        "https://youtu.be/ySQiDjQ1b5k",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "why-i-have-been-sent-tax-transcript",
        "Why have I been sent a tax return? - video transcript",
        "/business-account/help/transcript/why-sent-tax-return"
      )
      assertLinkById(
        doc,
        "used-to-file-link",
        "tell HMRC to stop Self Assessment.",
        "http://localhost:9020/business-account/self-assessment/stop"
      )
      assertLinkById(
        doc,
        "my-first-sa-video",
        "Video - My first Self Assessment tax return (opens in new tab)",
        "https://youtu.be/SSZ56xkMs48",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "my-first-sa-transcript",
        "Your first Self Assessment tax return - video transcript",
        "/business-account/help/transcript/new-your-first-tax-return"
      )
      assertLinkById(
        doc,
        "learn-more-sa",
        "learn more about Self Assessment (opens in new tab).",
        "https://www.gov.uk/guidance/help-and-support-for-self-assessment",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "tailor-tax-video",
        "Video - How do I tailor my Self Assessment tax return? (opens in new tab)",
        "https://youtu.be/Rr0LUxFQ33g",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "tailor-tax-transcript",
        "Tailor your tax return - video transcript",
        "/business-account/help/transcript/new-how-do-i-tailor-sa-tax"
      )
      assertLinkById(
        doc,
        "self-employed-tax-video",
        "Video - Your self-employed tax return (opens in new tab)",
        "https://youtu.be/revvcPm40IE",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "self-employed-tax-transcript",
        "Your self-employed tax return - video transcript",
        "/business-account/help/transcript/new-your-self-employed-tax-return"
      )
      assertLinkById(
        doc,
        "income-from-property-video",
        "Video - Income from property - what do I include on my tax return? (opens in new tab)",
        "https://youtu.be/jUVNt68r2h0",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "income-from-property-transcript",
        "If I have income from property, how do I fill in my tax return? - video transcript",
        "/business-account/help/transcript/new-income-from-property"
      )
      assertLinkById(
        doc,
        "who-must-file",
        "Who must file a tax return (opens in new tab)",
        "https://www.gov.uk/self-assessment-tax-returns/who-must-send-a-tax-return",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "need-to-change",
        "If you need to change your tax return (opens in new tab)",
        "https://www.gov.uk/self-assessment-tax-returns/corrections",
        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "how-to-pay-sa",
        "How to pay your Self Assessment",
        "/business-account/help/self-assessment/payment-and-penalties"
      )
      assertLinkById(
        doc,
        "sa-resource-list",
        "Self Assessment resource list (opens in new tab)",
        "https://www.gov.uk/topic/personal-tax/self-assessment",
        expectedOpensInNewTab = true
      )

    }
    "have correct Sa conditional link" in {
      val doc = asDocument(createView(Some(SaUtr("1234567890")))())

      assertLinkById(
        doc,
        "complete-and-file",
        "Complete and file your tax return (opens in new tab).",
        "http://localhost:8080/portal/self-assessment-file/1920/ind/1234567890/return?lang=eng",
        expectedOpensInNewTab = true
      )
    }
  }
}
