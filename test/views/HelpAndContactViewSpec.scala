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

    "contain heading ID" in {
      val doc = asDocument(createView(page = "help-with-bta")())
      doc.getElementsByTag("h1").attr("id") mustBe "help-and-contact"
    }

    "have correct links" when {
      val doc = asDocument(createView(page = "help-with-bta")())

      val linksToValidate = Seq(
        ("Help with your business tax account", "/business-account/help/help-with-bta"),
//        ("Change contact and account details", "/business-account/help/change-details"),
//        ("How to add a tax", "/business-account/help/how-to-add-tax"),
//        ("Payments and penalties", "/business-account/help/payments-and-penalties"),
//        ("Registering or stopping", "/business-account/help/registering-or-stopping"),
//        ("Help with SA tax return", "/business-account/help/help-with-sa"),
//        ("Get evidence of income - SA302", "/business-account/help/get-evidence-of-income"),
//        ("Expenses", "/business-account/help/expenses"),
//        ("Sign up for MTD for Income Tax (opens in new tab)", "/business-account/help/sign-up-for-MTD"),
//        ("Register or deregister", "/business-account/help/register-or-deregister"),
//        ("How to pay and deadlines", "/business-account/help/how-to-pay-and-deadlines"),
//        ("Correcting errors on returns", "/business-account/help/correcting-errors-on-returns"),
//        ("Get started", "/business-account/help/get-started-paye"),
//        ("View or correct submissions", "/business-account/help/view-or-correct-submissions"),
//        ("PAYE and CIS refunds", "/business-account/help/paye-and-cis-refunds"),
//        ("Changes in employee circumstances", "/business-account/help/change-in-employee-circumstances"),
//        ("If you stop being an employer", "/business-account/help/if-you-stop-being-an-employer"),
//        ("Register or add tax", "/business-account/help/register-or-add-tax"),
//        ("How to pay", "/business-account/help/how-to-pay"),
//        ("Closing a limited company", "/business-account/help/closing-limited-company"),
//        ("Get a copy of your Unique Taxpayer Reference (UTR)", "/business-account/help/get-copy-of-your-utr"),
//        ("Contact HMRC", "/business-account/help/contact-hmrc")
      )

      linksToValidate.foreach { case (linkText, url) =>
        s"contain '$linkText' link" in {
          assertLinkByText(doc, linkText, url)
        }

        def assertLinkByText(doc: Document, linkText: String, expectedUrl: String): Unit = {
          val links = doc.select("a")
          val link: Option[Element] = links.find(_.text() == linkText)
          assert(link.isDefined, s"Link with text '$linkText' not found")
          assert(link.get.attr("href") == expectedUrl, s"Link with text '$linkText' does not point to '$expectedUrl'")
        }
        }
      }
    }
}
