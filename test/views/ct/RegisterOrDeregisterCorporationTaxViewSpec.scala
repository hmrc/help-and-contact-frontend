/*
 * Copyright 2019 HM Revenue & Customs
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

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.ct.register_or_deregister_corporation_tax
import collection.JavaConverters._

class RegisterOrDeregisterCorporationTaxViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "ct.register_deregister"

  def createView = () => register_or_deregister_corporation_tax(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

  "/help/corporation-tax/register-or-tell-hmrc-you-are-no-longer-trading" must {

    behave like normalPage(createView, messageKeyPrefix)

    "have correct h2 headings" in {
      val listOfHeadings: List[String] = List(
        "How to register",
        "Make your company active",
        "What happens next",
        "Tell HMRC you are no longer trading"
      )

      val doc = asDocument(createView())
      val headings = doc.getElementsByTag("article").first.getElementsByTag("h2").asScala.toList.map(_.text())

      headings mustBe listOfHeadings
    }

    "have correct content" in {
      val doc = asDocument(createView())
      doc.text() must include("After you have registered your company with Companies House, you will need to register it for " +
        "Corporation Tax within 3 months of starting to do business.")
      doc.text() must include("This includes buying, selling, advertising, renting a property and employing someone. You can check " +
        "if you’re unsure what counts as starting to do business.")
      doc.text() must include("You may get a penalty if you register late.")
      doc.text() must include("You’ll need your company’s 10-digit Unique Taxpayer Reference (UTR) before you register for " +
        "Corporation Tax online.")
      doc.text() must include("This is posted to your company address by HM Revenue and Customs (HMRC), usually within a few days " +
        "of the company being registered with Companies House (incorporated).")
      doc.text() must include("Request your company’s UTR online if you did not get one after registering your company.")
      doc.text() must include("You will need to tell HMRC:")
      doc.text() must include("your company’s registration number")
      doc.text() must include("the date you started to do business (your company’s first accounting period will start from this date)")
      doc.text() must include("the date your annual accounts are made up to")
      doc.text() must include("If your company is registered as dormant and then starts trading, you need to register for Corporation Tax to tell HMRC your company is active.")
      doc.text() must include("HMRC will tell you the deadline for paying Corporation Tax. You will need to file a Company Tax Return, " +
        "even if you make a loss or have no Corporation Tax to pay.")
      doc.text() must include("To stop paying Corporation Tax, you will need to close the limited company.")
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "register-company-house",
        "registered your company",
        "https://www.gov.uk/limited-company-formation/register-your-company",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:registered your company",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)

      assertLinkById(
        doc,
        "guidance",
        "check if you’re unsure what counts as starting to do business",
        "https://www.gov.uk/guidance/corporation-tax-trading-and-non-trading",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:check if you’re unsure what counts as starting to do business",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)

      assertLinkById(
        doc,
        "register-for-hmrc-taxes",
        "register for Corporation Tax online",
        "http://localhost:8080/portal/business-registration/select-taxes?lang=eng",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:register for Corporation Tax online",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)

      assertLinkById(
        doc,
        "ask-for-utr",
        "Request your company’s UTR online",
        "http://localhost:9200/ask-for-copy-of-your-corporation-tax-utr",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:Request your company UTR online",
        expectedIsExternal = false,
        expectedOpensInNewTab = false)

      assertLinkById(
        doc,
        "accounting-periods",
        "accounting period",
        "https://www.gov.uk/corporation-tax-accounting-period",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:accounting period",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)

      assertLinkById(
        doc,
        "register_to_tell_company_active",
        "register for Corporation Tax",
        "http://localhost:8080/portal/business-registration/select-taxes?lang=eng",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:register for Corporation Tax"
      )

      assertLinkById(
        doc,
        "deadline-link",
        "Company Tax Return",
        "https://www.gov.uk/company-tax-returns",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:Company Tax Return",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)

      assertLinkById(
        doc,
        "close-limited-company",
        "close the limited company",
        "https://www.gov.uk/closing-a-limited-company",
        "link - click:Register for Corporation Tax or tell HMRC you are no longer trading:close the limited company",
        expectedIsExternal = true,
        expectedOpensInNewTab = true)
    }
  }
}
