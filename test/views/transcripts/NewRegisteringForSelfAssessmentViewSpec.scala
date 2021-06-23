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

package views.transcripts

import play.twirl.api.{Html, HtmlFormat}
import views.behaviours.ViewBehaviours
import views.html.transcripts.new_registering_for_self_assessment

import scala.collection.JavaConverters._

class NewRegisteringForSelfAssessmentViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "newRegistering.for.self.assessment.transcript"

  def createView: () => Html =
    () =>
      inject[new_registering_for_self_assessment].apply(frontendAppConfig)(Some(HtmlFormat.empty))(
        fakeRequest,
        messages
    )

  "RegisteringForSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .attr("id") mustBe "new-registering-for-sa-transcript"
    }

    "have correct content" in {
      val bulletPointList = List(
        "self-employed",
        "a partner in a partnership",
        "or for another reason such as untaxed income."
      )

        val bulletPointList2 = List(
          "if your turnover is more than a thousand pounds in a tax year",
          "to prove you’re self-employed",
          "or to pay voluntary Class 2 National Insurance contributions."
        )

      val contentList: List[String] = List(
        "You may need to register for Self Assessment if you’re:",
        bulletPointList.mkString(" "),
        "If you didn’t send a tax return last year, you need to register by 5th October following the end of the tax year.",
        "Here, we’ll explain the different ways to register.",
        "If you’re self-employed, you’ll need to register:",
        bulletPointList2.mkString(" "),
        "If you’ve sent a tax return before, but didn’t last year, use your existing Self Assessment Unique Taxpayer Reference, or UTR – and re-register online using form CWF1.",
        "Otherwise, register online.",
        "We’ll send you a letter with your UTR and set up your account for the Self Assessment online service. You’ll get another letter a few days later with a code to activate your online service. Use this the first time you sign in",
        "Partnerships must be registered by a nominated partner, but each partner needs to register individually as well – using form SA401 and the UTR for the partnership.",
        "If you need to register for another reason, use form SA1.",
        "Once you’ve registered, if you already have a UTR and online account, you can use them to send your online tax return.",
        "If not, we’ll send your UTR in the post within ten working days.",
        "Use your UTR to create a new online account and sign up for the Self Assessment online service. We’ll then send you a code to activate your account within seven working days of signing up.",
        "You can find out more about Self Assessment on GOV.UK and watch our other helpful videos on YouTube."
      )

      val doc = asDocument(createView())
      val contentDiv = doc
        .getElementById("new-registering-for-sa-transcript")
      val texts: List[String] = contentDiv
        .children()
        .asScala
        .toList
        .map(_.text())

      contentList.zip(texts).foreach {
        case (content, element) => element mustBe content
      }

      val bullets: List[String] =
        contentDiv.select("ul.list.list-bullet>li").asScala.toList.map(_.text)
      bulletPointList.zip(bullets).foreach {
        case (content, element) => element mustBe content
      }
    }
  }

}
