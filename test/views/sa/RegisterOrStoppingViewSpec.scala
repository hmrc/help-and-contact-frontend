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

package views.sa

import models.PageType
import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours
import views.html.sa.registering_or_stopping

class RegisterOrStoppingViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "register_or_stopping"

  def createView = () => registering_or_stopping(PageType.RegisteringOrStopping.name, frontendAppConfig)(messages)

  "Self Assessment Expenses view" must {

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "page-title-registering-or-stopping"
    }

    "contain correct heading" in {
      val doc = asDocument(createView())

      val h1s = doc.getElementsByTag("h1")
      h1s.size() mustBe 1
      h1s.first().text() mustBe "Self Assessment: registering or stopping"
    }

    "contain correct content" in {
      val doc = asDocument(createView())
      doc.text() must include("When you register for Self Assessment as self employed, you are also registering for")

      doc.text() must include("Your Self Assessment Unique Taxpayer Reference (UTR) is a 10 digit number. You can find it on:")

      doc.text() must include("your Welcome to Self Assessment letter (SA250)")

      doc.text() must include("tax returns you completed in previous years")

      doc.text() must include("other documents from HMRC")

      doc.text() must include("If you did not file a tax return last year and have to file one this year,")

      doc.text() must include("You will need the Unique Taxpayer Reference (UTR) for the partnership and your own personal UTR if you already have one.")

      doc.text() must include("If it is a new partnership, the nominated partner will also have to register the partnership.")

      doc.text() must include("You must")

      doc.text() must include("This could be because you are:")

      doc.text() must include("stopping self-employment")

      doc.text() must include("no longer trading as a sole trader")

      doc.text() must include("ending or leaving a business partnership")

      doc.text() must include("You will need to send final tax returns and tell employees that you are closing your business.")

      doc.text() must include("We will stop your Self Assessment and cancel your Class 2 National Insurance contributions.")

      doc.text() must include("Stopping your Class 2 National Insurance contributions may mean you lose certain benefits.")

      doc.text() must include("You can choose to stay registered for these contributions after stopping Self Assessment if you" +
        " want to make voluntary payments. This will allow you to claim benefits like Tax-Free Childcare or Maternity Allowance.")

    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "class-2-ni-contributions",
        "Class 2 National Insurance contributions",
        "https://www.gov.uk/self-employed-national-insurance-rates",

        expectedOpensInNewTab = false
      )
      assertLinkById(
        doc,
        "find-UTR",
        "You can find your UTR if you do not know it. (opens in new tab)",
        "https://www.gov.uk/find-lost-utr-number",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "sa1-register",
        "use form SA1 to register for Self Assessment. (opens in new tab)",
        "https://www.gov.uk/government/publications/self-assessment-register-for-self-assessment-and-get-a-tax-return-sa1",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "file-register-sa",
        "register for Self Assessment.",
        "http://localhost:9730/business-account/add-tax/self-assessment/have-sa-utr"

      )
      assertLinkById(
        doc,
        "registering-partnership",
        "Find out more about registering as a partner or partnership (opens in new tab)",
        "https://www.gov.uk/register-for-self-assessment/partner-or-partnership",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "learn-sa",
        "Learn about Self Assessment (opens in new tab)",
        "https://www.gov.uk/guidance/help-and-support-for-self-assessment",

        expectedOpensInNewTab = true
      )
      assertLinkById(
        doc,
        "tell-hmrc",
        "tell HMRC if you do not need to file tax returns any more.",
        "http://localhost:9020/business-account/self-assessment/stop"

      )

      assertLinkById(
        doc,
        "viewing-register-online-sa-not-self-employed-video",
        "Video – How do I register online for Self Assessment if I’m not self-employed? (opens in a new tab)",
        "https://www.youtube.com/watch?v=6ia0F3zitD0",

        expectedOpensInNewTab = true
      )

      assertLinkById(
        doc,
        "viewing-register-online-sa-self-employed-video",
        "Video – How do I register online for Self Assessment if I’m self-employed? (opens in a new tab)",
        "https://www.youtube.com/watch?v=bovXqb7H4T8",

        expectedOpensInNewTab = true
      )

    }

  }

}
