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
import views.html.transcripts.why_sent_tax_return

import scala.collection.JavaConverters._

class WhySentTaxReturnViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "whySentTaxReturnTranscript"

  def createView = () => inject[why_sent_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)

  "WhySentTaxReturn view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID with correct title" in {
      val doc = asDocument(createView())
      doc.getElementsByTag("h1").attr("id") mustBe "why-sent-tax-return-transcript"
      doc.getElementsByTag("h1").text() mustBe "Video transcript – What to do if you’ve been sent a tax return"
    }

    "have correct content" in {
      val doc = asDocument(createView())

      val elements = doc.getElementsByTag("article").first().getElementsByTag("p").asScala.toList.map(_.text())

      val contentList = List(
        "You’ll usually be sent a tax return if you’re registered as self-employed, but there are plenty of other reasons why you may need to fill one in.",
        "You can check if you need to fill in a Self Assessment tax return quickly and easily on GOV.UK using the link at the end of this video. It’s not connected to HM Revenue and Customs online services so it’s completely anonymous.",
      "If you’re sent a tax return, or if you get an email or letter from HMRC telling you to complete one, you must do it - even if you don’t have any tax to pay.",
       "If you don’t send a tax return back by the deadline you may have to pay a penalty. If you used to send a tax return but don’t need to send one for the last tax year, contact HMRC to close your Self Assessment account. You must also tell HMRC if you’ve stopped being self-employed.",
     "You can do all of this and find out much more on GOV.UK."
      )

      contentList.zipAll(elements, "", "").foreach {
        case (content, element) => element mustBe content
      }

    }
    "have correct links" in {
      val doc = asDocument(createView())
      assertLinkById(
        doc,
        "gov-link",
        "GOV.UK",
        "https://www.gov.uk/")
    }
  }
}