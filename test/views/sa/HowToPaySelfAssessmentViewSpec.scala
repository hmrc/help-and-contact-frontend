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

import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.api.i18n.{Lang, Messages}
import play.twirl.api.HtmlFormat
import models.{SaUtr, SaUtrGenerator}
import views.behaviours.ViewBehaviours
import views.html.sa.how_to_pay_self_assessment

import scala.util.Random

class HowToPaySelfAssessmentViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "howToPaySelfAssessment"

  def fakeServiceInfoRequest(saUtr: Option[SaUtr] = None) = {
    ServiceInfoRequest(AuthenticatedRequest(fakeRequest, saUtr, None), HtmlFormat.empty)
  }

  def createView(saUtr: Option[SaUtr] = None) = () => inject[how_to_pay_self_assessment].apply(frontendAppConfig)(HtmlFormat.empty)(fakeServiceInfoRequest(saUtr), messages)

  "HowToPaySelfAssessment view" must {
    behave like normalPage(createView(), messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView()())
      doc.getElementsByTag("h1").attr("id") mustBe "how-to-pay-sa"
    }

    "have correct h2 headings" in {
      val doc = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Viewing your calculation")
      headings must include("How do I pay my Self Assessment tax bill?")
      headings must include("Penalties and appeals")
      headings must include("Appeal against a penalty")
      headings must include("Check the status of an appeal")
      headings must include("Further help with penalties")
    }

    "have correct content" in {
      val doc = asDocument(createView()())
      doc.text() must include("HMRC’s online tax return automatically works out how much you need to pay. " +
        "You fill in your figures and once you have checked that everything is correct, you can view your calculation.")

      doc.text() must include("This shows how your tax bill has been worked out. It shows your income, " +
        "your personal allowance, the tax due, any class 4 and class 2 National Insurance contributions, " +
        "and the payments that are due on 31 January 2018. You can print a copy of this for your own records.")

      doc.text() must include("The deadlines for paying are 31 January for a tax bill for the previous year and your first payment on account, " +
        "and 31 July for your second payment on account. Make sure you pay HMRC on time. You will be charged interest and may have to " +
        "pay a penalty if your payment is late. You do not have to wait until 31 January or 31 July to pay. If you do, " +
        "you might miss the deadline, depending on how you pay.")

      doc.text() must include("If you are paying your bill the same or the next day you can use:")
      doc.text() must include("online or telephone banking (Faster Payments)")
      doc.text() must include("CHAPS")
      doc.text() must include("a debit or credit card online")
      doc.text() must include("a bank or building society")
      doc.text() must include("If you have longer to pay your bill you can use:")
      doc.text() must include("Bacs (3 working days)")
      doc.text() must include("Direct Debit (3 working days if you already have one set up, 5 working days if you need to set one up)")
      doc.text() must include("Cheque by post (3 working days)")
      doc.text() must include("You’ll have a penalty showing on your account if you:")
      doc.text() must include("sent in your tax return late")
      doc.text() must include("paid tax late")
      doc.text() must include("didn’t pay enough tax")

      doc.text() must include("You can appeal if you have a reasonable excuse, for example you had an unexpected stay in hospital. You must appeal within 30 days of the date the penalty was sent to you.")

      doc.text() must include("You can:")
      doc.text() must include("appeal a £100 Self Assessment late filing penalty online (opens in new tab)")
      doc.text() must include("appeal any late filing or late payment penalty using form SA370 (opens in new tab)")
      doc.text() must include("If you get your penalty notice by email, you can fill in form SA370 to appeal against Self Assessment penalties for late filing and late payment, or you can write to HMRC with your reasons for appealing.")
      doc.text() must include("Send your form or letter to HMRC's address for Self Assessment enquiries.")
      doc.text() must include("You'll need a breakdown of your penalties and interest.")

      doc.text() must include("HMRC will send you contact details for the office dealing with your case within 15 days of receiving your " +
        "appeal.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "viewing-self-assessment-calculation-video",
        "Video - Viewing your Self Assessment tax return calculation (opens in new tab)",
        "https://youtu.be/wq35KqfGCjo",
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "viewing-self-assessment-calculation-transcript",
        "Viewing your Self Assessment tax return calculations - video transcript",
        "/business-account/help/transcript/viewing-your-self-assessment-calculation")
      assertLinkById(
        doc,
        "paying-your-self-assessment-bill-video",
        "Video - How do I pay my Self Assessment tax bill? (opens in new tab)",
        "https://youtu.be/vRxLHyNmWp4",
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "paying-your-self-assessment-bill-transcript",
        "How do I pay my Self Assessment tax bill? - video transcript",
        "/business-account/help/transcript/paying-your-self-assessment-tax-bill")
      assertLinkById(
        doc,
        "more-information",
        "More information about paying your Self Assessment tax bill",
        "https://www.gov.uk/pay-self-assessment-tax-bill")
      assertLinkById(
        doc,
        "budgeting-for-sa-bill-video",
        "Video - How do I budget for my Self Assessment tax bill? (opens in new tab)",
        "https://youtu.be/XaqY3qhDXGo",
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "budgeting-for-sa-bill-transcript",
        "How do I budget for my Self Assessment tax bill? - video transcript",
        "/business-account/help/transcript/budgeting-your-self-assessment-tax-bill")
      assertLinkById(
        doc,
        "reasonable-excuses",
        "reasonable excuse",
        "https://www.gov.uk/tax-appeals/reasonable-excuses")
      assertLinkById(
        doc,
        "form-SAASUB",
        "appeal a £100 Self Assessment late filing penalty online (opens in new tab)",
        "/digital-forms/form/self-assessment-appeal-late-filing-penalty/draft/guide",
        false,
        true,
        false)
      assertLinkById(
        doc,
        "form-SA370",
        "appeal any late filing or late payment penalty using form SA370 (opens in new tab)",
        "https://www.gov.uk/government/publications/self-assessment-appeal-against-penalties-for-late-filing-and-late-payment-sa370",
        false,
        true)
      assertLinkById(
        doc,
        "address-for-sa-enquiries",
        "address for Self Assessment enquiries.",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/self-assessment")
      assertLinkById(
        doc,
        "self-assessment-penalties-video",
        "Video - Self Assessment penalties (opens in new tab)",
        "https://youtu.be/tIqsbnmNqzA",
        expectedOpensInNewTab = true)
      assertLinkById(
        doc,
        "self-assessment-penalties-transcript",
        "Self Assessment penalties - video transcript",
        "/business-account/help/transcript/self-assessment-penalties")
    }

  }

  "when user has no enrolment for self assessment" should {

    "have no penalties link" in {
      val doc = asDocument(createView()())
      Option(doc.getElementById("penalties")) mustBe None
    }

    "have no interest link" in {
      val doc = asDocument(createView()())
      Option(doc.getElementById("interest")) mustBe None
    }
  }

  "when user has an active enrolment for self assessment" should {

    "have a penalties link" in {
      val utr = new SaUtrGenerator(new Random()).nextSaUtr
      val doc = asDocument(createView(Some(utr))())
      assertLinkById(
        doc,
        "penalties",
        "View your Self Assessment penalties",
        s"http://localhost:8080/portal/self-assessment/ind/$utr/account/penalties?lang=eng")
    }

    "have an interest link" in {
      val utr = new SaUtrGenerator(new Random()).nextSaUtr
      val doc = asDocument(createView(Some(utr))())
      assertLinkById(
        doc,
        "interest",
        "View your Self Assessment interest",
        s"http://localhost:8080/portal/self-assessment/ind/$utr/account/interests?lang=eng")
    }
  }

  "when viewed in Welsh" should {
    "have a link to welsh helpline" in {
      val doc = asDocument(inject[how_to_pay_self_assessment].apply(frontendAppConfig)(HtmlFormat.empty)(fakeServiceInfoRequest(None), messagesApi.preferred(Seq(Lang("cy")))))

      assertLinkById(
        doc,
        "address-for-sa-enquiries",
        "Wasanaeth Cwsmeriaid Cymraeg CThEM.",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/welsh-language-helplines")
    }
  }
}
