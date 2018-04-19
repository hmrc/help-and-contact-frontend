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

    "have a h2 heading of 'Registering for Self Assessment'" in {
      val doc = asDocument(createView())
      doc.text() must include("Registering for Self Assessment")
    }

    "contain You must register to file " in {
      val doc = asDocument(createView())
      doc.text() must include("You must register to file a Self Assessment tax return within 6 months of the end of the tax year when" +
        " you became self-employed or received income that you need to pay tax on. A tax year ends on 5 April. " +
        "If you do not register, you could be fined.")
    }

    "have a h2 heading of 'If you have sent tax returns before'" in {
      val doc = asDocument(createView())
      doc.text() must include("If you have sent tax returns before")
    }

    "contain 'If you hae sent tax returns before'" in {
      val doc = asDocument(createView())
      doc.text() must include("If you have sent tax returns before you will need your 10-digit Unique Taxpayer Reference or UTR. " +
        "You will also be able to use an online account you previously set up to file your tax return but you still need to register.")
    }

    "have a h2 heading of 'Register if you are self-employed and you have sent a tax return before'" in {
      val doc = asDocument(createView())
      doc.text() must include("Register if you are self-employed and you have sent a tax return before")
    }

    "contain 'You will need to find your 10-digit Unique Taxpayer Reference" in {
      val doc = asDocument(createView())
      doc.text() must include("You will need to find your 10-digit Unique Taxpayer Reference (UTR) from when you registered for Self Assessment previously.")
    }

    "have a h2 heading of 'Register if you are self-employed and you have not sent a tax return before'" in {
      val doc = asDocument(createView())
      doc.text() must include("Register if you are self-employed and you have not sent a tax return before")
    }

    "contain 'You will get a letter within 10 working days" in {
      val doc = asDocument(createView())
      doc.text() must include("You will get a letter within 10 working days (21 if you are abroad) with your Unique Taxpayer Reference (UTR). " +
        "You will need this to enrol for Self Assessment online services so you can file your return online.")
    }

    "have a h2 heading of 'Activate your online account'" in {
      val doc = asDocument(createView())
      doc.text() must include("Activate your online account")
    }

    "contain 'You will get an activation code in the post within 10 working days'" in {
      val doc = asDocument(createView())
      doc.text() must include("You will get an activation code in the post within 10 working days of enrolling (21 if you are abroad). " +
        "You will need this when you first log in to your online account. " +
        "You can replace an activation code if you do not receive it or you lose it.")
    }

    "contain 'You will then need to create a Government'" in {
      val doc = asDocument(createView())
      doc.text() must include("You will then need to create a Government Gateway account. " +
        "Enter your personal details and create a password. A User ID is given on-screen. " +
        "Keep this safe along with your password as you will need them whenever you sign in online.")
    }
  }
}
