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
import views.html.transcripts.new_your_first_tax_return

import scala.collection.JavaConverters._

class NewYourFirstTaxReturnSpec extends ViewBehaviours {

  val messageKeyPrefix = "new.yourSelfEmployedTaxReturnTranscript"

  def createView = () => inject[new_your_first_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "NewIncomeFromProperty view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "new-yourSelfEmployedTaxReturnTranscript-heading"
    }

    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/",
        expectedOpensInNewTab = true
      )

    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "Do you need to complete a Self Assessment tax return?",
        "To help you find out, you can check on GOV.UK by answering some questions.",
        "If you do need to complete a tax return, you must do it or you could be fined, even if you don’t owe any tax.",
        "The tax year ends on the 5 April and you need to register for Self Assessment by the 5 October, or you may be charged a penalty. " +
          "You can register on GOV.UK – there’s another short You Tube video that explains how to do this.",
        "Your tax return, which covers the tax year that ended in April, is due by the 31" +
          " January the following year. Once you’ve registered and activated your online account, you’re ready to start.",
        "Make sure you’ve got your information and records to hand, as you’ll need to fill in details of all your income.",
        "Go to GOV.UK and login with the user ID and password you received when you registered online.",
        "First, we’ll need some details about you. The online screens will guide you – if you get stuck," +
          " there are prompts on screen and links that explain more. For example, if you enter your date of birth the wrong way, the box will light up red.",
        "In the section called ’Tailor your return’ you answer yes or no to a series of questions – this means you only fill" +
          " in the sections that apply to you. If you’re not sure about a question, click on the ‘Help about’ link for guidance.",
        "The Save and continue button will save what you’ve entered so far; it’s handy if you want to stop and come back to it later.",
        "When you’ve completed your return, check it through. You can go back if you need to correct anything.",
        "Use View your calculation to find out if you owe tax and how much.",
        "Now you can save a copy for your records. In the Submit return section, you’ll need your user" +
          " ID and password to send it to HMRC. Finally, you’ll get an online message to confirm that we’ve received it.",
        "You can find out more about Self Assessment on GOV.UK and watch our other helpful videos and webinars on YouTube."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }
    }
  }
}


