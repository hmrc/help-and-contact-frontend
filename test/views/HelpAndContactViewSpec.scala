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

package views

import models.PageType
import org.jsoup.nodes.{Document, Element}
import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.help_and_contact
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.twirl.api.TwirlHelperImports.twirlJavaCollectionToScala


class HelpAndContactViewSpec extends ViewBehaviours with GuiceOneAppPerSuite {

  val messageKeyPrefix = "help_and_contact"

  val serviceInfoContent: Option[Html] = Some(Html("<div>Service info content</div>"))

  def createView(dynamicHtmlContent: Html = HtmlFormat.empty, page: String): () => HtmlFormat.Appendable =
    () =>
      inject[help_and_contact].apply(frontendAppConfig)(serviceInfoContent,
        dynamicContent = dynamicHtmlContent,
        name = page, pageTypes = PageType.values)(
        fakeRequest,
        messages
      )

  "Help and contact view" must {

    behave like normalPage(createView(page="help-with-bta"), messageKeyPrefix)

    "behave like a page with dynamic content" when {
      "dynamic content is provided" must {
        "include the dynamic content in the view" in {
          val dynamicContent = Html("<p>Test dynamic content</p>")
          val doc = asDocument(createView(dynamicContent, page = "help-with-bta")())
          assertContainsText(doc, "Test dynamic content")
        }
      }
    }

    "have correct links" when {
      val doc = asDocument(createView(page = "help-with-bta")())

      val linksToValidate = Seq(
        ("nav-link-help-with-bta", "Help with your business tax account", "/business-account/help/help-with-your-business-tax-account"),
        ("nav-link-change-contact-and-account-details", "Change contact and account details", "/business-account/help/change-your-details"),
        ("nav-link-how-to-add-tax", "How to add a tax", "/business-account/help/how-to-add-tax"),
        ("nav-link-payments-and-penalties", "Payments and penalties", "/business-account/help/self-assessment/payment-and-penalties"),
        ("nav-link-registering-or-stopping", "Registering or stopping", "/business-account/help/self-assessment/register-or-stopping"),
        ("nav-link-help-with-sa-tax-return", "Help with SA tax return", "/business-account/help/self-assessment/help-with-return"),
        ("nav-link-get-evidence-of-income", "Get evidence of income - SA302", "/business-account/help/self-assessment/evidence-of-income"),
        ("nav-link-expenses", "Expenses", "/business-account/help/self-assessment/expenses"),
        ("nav-link-sign-up-for-mtd", "Sign up for MTD for Income Tax (opens in new tab)", "https://www.gov.uk/guidance/sign-up-your-business-for-making-tax-digital-for-income-tax"),
        ("nav-link-register-or-deregister-vat", "Register or deregister", "/business-account/help/vat/register-or-deregister"),
        ("nav-link-how-to-pay-vat-and-deadlines", "How to pay and deadlines", "/business-account/help/vat/how-to-pay"),
        ("nav-link-correcting-errors-on-returns", "Correcting errors on returns", "/business-account/help/correcting-errors-on-returns"),
        ("nav-link-get-started-paye", "Get started", "/business-account/help/epaye/get-started"),
        ("nav-link-view-or-correct-your-submissions", "View or correct your submissions", "/business-account/help/epaye/view-check-correct-submissions"),
        ("nav-link-paye-and-cis-refunds", "PAYE and CIS refunds", "/business-account/help/refunds"),
        ("nav-link-changes-in-employee-circumstances", "Changes in employee circumstances", "/business-account/help/epaye/change-employee-circumstances"),
        ("nav-link-stop-being-an-employer", "If you stop being an employer", "/business-account/help/stop-being-an-employer"),
        ("nav-link-register-add-corporation-tax", "Register or add Corporation Tax", "/business-account/help/register-add-corporation-tax"),
        ("nav-link-how-to-pay", "How to pay and deadlines", "/business-account/help/corporation-tax/how-to-pay"),
        ("nav-link-closing-limited-company", "Closing a limited company", "/business-account/help/closing-limited-company"),
        ("nav-link-ask-your-corporation-tax-utr", "Get a copy of your Unique Taxpayer Reference (UTR)", "/business-account/help/ask-for-copy-of-your-corporation-tax-utr"),
        ("nav-link-contact-hmrc", "Contact HMRC", "/business-account/help/corporation-tax/contact-hmrc")
      )

      linksToValidate.foreach { case (id, linkText, url) =>
        s"contain link with id '$id', text '$linkText', and URL '$url'" in {
          assertLinkById(doc, id, linkText, url)
        }
      }

      def assertLinkById(doc: Document, id: String, expectedText: String, expectedUrl: String): Unit = {
        val link = doc.select(s"a#$id").first()
        assert(link != null, s"Link with id '$id' not found")
        assert(link.text() == expectedText, s"Link with id '$id' does not have expected text '$expectedText'")
        assert(link.attr("href") == expectedUrl, s"Link with id '$id' does not point to '$expectedUrl'")
      }
      }
    }
}
