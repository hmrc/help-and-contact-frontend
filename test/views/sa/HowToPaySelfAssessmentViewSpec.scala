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

import models.requests.{AuthenticatedRequest, ServiceInfoRequest}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.domain.{SaUtr, SaUtrGenerator}
import views.behaviours.ViewBehaviours
import views.html.sa.how_to_pay_self_assessment

import scala.util.Random

class HowToPaySelfAssessmentViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "howToPaySelfAssessment"

  def fakeServiceInfoRequest(saUtr: Option[SaUtr] = None) = {
    ServiceInfoRequest(AuthenticatedRequest(fakeRequest, saUtr), HtmlFormat.empty)
  }

  def createView(saUtr: Option[SaUtr] = None) = () => how_to_pay_self_assessment(frontendAppConfig)(HtmlFormat.empty)(fakeServiceInfoRequest(saUtr), messages)

  "HowToPaySelfAssessment view" must {
    behave like normalPage(createView(), messageKeyPrefix)

    "have correct h2 headings" in {
      val doc = asDocument(createView()())
      val headings = doc.getElementsByTag("h2").toString
      headings must include("Viewing your calculation")
      headings must include("Paying your Self Assessment tax bill")
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
      doc.text() must include(" (Faster Payments)")
      doc.text() must include("You need a paying-in slip from HMRC to pay at a bank or building society.")
      doc.text() must include("If you have longer to pay your bill you can use:")
      doc.text() must include(" (3 working days)")
      doc.text() must include(" (3 working days if you already have one set up, 5 working days if you need to set one up)")
      doc.text() must include("You’ll have a penalty showing on your account if you:")
      doc.text() must include("sent in your tax return late")
      doc.text() must include("paid tax late")
      doc.text() must include("didn’t pay enough tax")

      doc.text() must include("You can appeal if you have a ‘reasonable excuse’, for example you had an unexpected stay in hospital. " +
        "You must appeal within 30 days of the date the penalty was sent to you.")

      doc.text() must include("You can’t appeal a penalty online or by phone. Find out more about reasonable excuses and how to appeal " +
        "by post.")

      doc.text() must include("If you get your penalty notice by email from HMRC, you can either fill in form SA370 or write to HMRC, " +
        "giving your reasons for appealing.")

      doc.text() must include("Send your form or letter to HMRC’s ")
      doc.text() must include("You’ll need a breakdown of your penalties and interest.")

      doc.text() must include("HMRC will send you contact details for the office dealing with your case within 15 days of receiving your " +
        "appeal.")
    }

    "have correct links" in {
      val doc = asDocument(createView()())
      assertLinkById(
        doc,
        "viewing-self-assessment-calculation-transcript",
        "Viewing your calculation - video transcript",
        "/business-account/help/transcript/viewing-your-self-assessment-calculation",
        "HowToPaySa:click:ViewSelfAssessmentCalculationTranscript")
      assertLinkById(
        doc,
        "paying-your-self-assessment-bill-transcript",
        "Paying your Self Assessment tax bill - video transcript",
        "/business-account/help/transcript/paying-your-self-assessment-tax-bill",
        "HowToPaySa:click:PayingSelfAssessmentBillTranscript")
      assertLinkById(
        doc,
        "online-or-telephone-banking",
        "online or telephone banking",
        "https://www.gov.uk/pay-self-assessment-tax-bill/bank-details?utm_source=sa-overview&utm_medium=internal-link&utm_campaign=online-or-telephone-banking",
        "HowToPaySa:click:OnlineOrTelephoneBanking")
      assertLinkById(
        doc,
        "chaps",
        "CHAPS",
        "https://www.gov.uk/pay-self-assessment-tax-bill/bank-details?utm_source=sa-overview&utm_medium=internal-link&utm_campaign=chaps",
        "HowToPaySa:click:CHAPS")
      assertLinkById(
        doc,
        "debit-or-credit-card",
        "a debit or credit card online",
        "https://www.gov.uk/pay-self-assessment-tax-bill/by-debit-or-credit-card-online?utm_source=sa-overview&utm_medium=internal-link&utm_campaign=debit-or-credit-card-online",
        "HowToPaySa:click:DebitOrCreditCard")
      assertLinkById(
        doc,
        "bank-or-building-society",
        "a bank or building society",
        "https://www.gov.uk/pay-self-assessment-tax-bill/bank-or-building-society?utm_source=sa-overview&utm_medium=internal-link&utm_campaign=bank-or-building-society",
        "HowToPaySa:click:BankOrBuildingSociety")
      assertLinkById(
        doc,
        "bacs",
        "Bacs",
        "https://www.gov.uk/pay-self-assessment-tax-bill/bank-details",
        "HowToPaySa:click:Bacs")
      assertLinkById(
        doc,
        "direct-debit",
        "Direct Debit",
        "https://www.gov.uk/pay-self-assessment-tax-bill/direct-debit",
        "HowToPaySa:click:DirectDebit")
      assertLinkById(
        doc,
        "cheque-by-post",
        "Cheque by post",
        "https://www.gov.uk/pay-self-assessment-tax-bill/by-post",
        "HowToPaySa:click:ChequeByPost")
      assertLinkById(
        doc,
        "budgeting-for-sa-bill-transcript",
        "Budgeting for your Self Assessment tax bill - video transcript",
        "/business-account/help/transcript/budgeting-your-self-assessment-tax-bill",
        "HowToPaySa:click:BudgetingSaBillTranscript")
      assertLinkById(
        doc,
        "reasonable-excuses",
        "reasonable excuses",
        "https://www.gov.uk/tax-appeals/reasonable-excuses",
        "HowToPaySa:click:ReasonableExcuses")
      assertLinkById(
        doc,
        "how-to-appeal",
        "how to appeal",
        "https://www.gov.uk/tax-appeals/penalty",
        "HowToPaySa:click:HowToAppeal")
      assertLinkById(
        doc,
        "form-SA370",
        "form SA370",
        "https://www.gov.uk/government/publications/self-assessment-appeal-against-penalties-for-late-filing-and-late-payment-sa370",
        "HowToPaySa:click:FormSA370")
      assertLinkById(
        doc,
        "address-for-sa-enquiries",
        "address for Self Assessment enquiries",
        "https://www.gov.uk/government/organisations/hm-revenue-customs/contact/self-assessment",
        "HowToPaySa:click:AddressForSaEnquiries")
      assertLinkById(
        doc,
        "self-assessment-penalties-transcript",
        "Self Assessment penalties - video transcript",
        "/business-account/help/transcript/self-assessment-penalties",
        "HowToPaySa:click:SelfAssessmentPenaltiesTranscript")
    }

    "have youtube url in html for each embedded video" in {
      val doc = asDocument(createView()())
      val listOfVideoId: List[String] = List("Pu0XogdzFH4", "sI03I5ZdDrw", "s24M389lWJg", "5HpAwHGBS1E")
      listOfVideoId.foreach(id => doc.toString must include(s"http://www.youtube.com/embed/$id?autoplay=0"))
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
        "penalties",
        s"http://localhost:8080/portal/self-assessment/ind/$utr/account/penalties?lang=eng",
        "HowToPaySa:click:Penalties")
    }

    "have an interest link" in {
      val utr = new SaUtrGenerator(new Random()).nextSaUtr
      val doc = asDocument(createView(Some(utr))())
      assertLinkById(
        doc,
        "interest",
        "interest",
        s"http://localhost:8080/portal/self-assessment/ind/$utr/account/interests?lang=eng",
        "HowToPaySa:click:Interest")
    }
  }
}
