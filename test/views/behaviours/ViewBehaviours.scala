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

package views.behaviours

import play.api.mvc.{AnyContent, Request}
import play.twirl.api.HtmlFormat
import views.ViewSpecBase
import play.api.i18n.{I18nSupport, MessagesApi}

trait ViewBehaviours extends ViewSpecBase {

  implicit val request: Request[AnyContent] = fakeRequest


  def normalPage(view: () => HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 expectedGuidanceKeys: String*) = {

    "behave like a normal page" when {
      "rendered" must {
        "have the correct banner title" in {
          val doc = asDocument(view())
          val nav = doc.getElementById("proposition-menu")
          val span = nav.children.first
          span.text mustBe messages("site.service_name")
        }

        "display the correct browser title" in {
          val doc = asDocument(view())
          val pagetitle = messages(s"$messageKeyPrefix.title")
          assertEqualsValue(
            doc,
            "title",
            messages("site.service_title", pagetitle)
          )
        }

        "display the correct page title" in {
          val doc = asDocument(view())
          assertPageTitleEqualsMessage(doc, s"$messageKeyPrefix.heading")
        }

        "display the correct guidance" in {
          val doc = asDocument(view())
          for (key <- expectedGuidanceKeys)
            assertContainsText(doc, messages(s"$messageKeyPrefix.$key"))
        }

        "display language toggles" in {
          val doc = asDocument(view())
          assertRenderedById(doc, "cymraeg-switch")
        }

        "display the sign out link" in {
          val doc = asDocument(view())
          assertLinkById(
            doc,
            "logOutNavHref",
            "Sign out",
            "http://localhost:9020/business-account/sso-sign-out",
            "link - click:Help and Contact:Sign out"
          )
        }

        "contain the platform help links section with a link to the accessibility statement" in  {
          val doc = asDocument(view())
          val linkSection = doc.select("ul.platform-help-links")
          linkSection.size() mustBe 1
          linkSection.select("li").size mustBe 5
          linkSection.select("li > a").get(1).attr("href") must include("/accessibility-statement/business-tax-account")
          linkSection.select("li > a").get(1).text mustBe "Accessibility statement"
        }
      }
    }
  }

  def pageWithBackLink(view: () => HtmlFormat.Appendable) = {

    "behave like a page with a back link" must {
      "have a back link" in {
        val doc = asDocument(view())
        assertRenderedById(doc, "back-link")
      }
    }
  }
}
