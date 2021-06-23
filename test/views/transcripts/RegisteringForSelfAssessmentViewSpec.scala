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
import views.html.transcripts.registering_for_self_assessment

import scala.collection.JavaConverters._

class RegisteringForSelfAssessmentViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "registering.for.self.assessment.transcript"

  def createView: () => Html =
    () =>
      inject[registering_for_self_assessment].apply(frontendAppConfig)(Some(HtmlFormat.empty))(
        fakeRequest,
        messages
    )

  "RegisteringForSelfAssessment view" must {
    behave like normalPage(createView, messageKeyPrefix)

    "contain heading ID" in {
      val doc = asDocument(createView())
      doc
        .getElementsByTag("h1")
        .attr("id") mustBe "registering-for-sa-transcript"
    }

    "have correct content" in {
      val bulletPointList = List(
        "if your turnover is more than £1000 in a tax year",
        "to prove you’re self-employed",
        "or to pay voluntary Class 2 National Insurance contributions"
      )
      val contentList: List[String] = List(
        "You’ll need to register for Self Assessment if you’re self-employed, and in some cases when you’re not self-employed.",
        "For example, because you’re a partner in a partnership – or another reason such as lump sum. Here we’ll explain the different ways to register.",
        "If you’re self-employed or a sole trader, you’ll need to register:",
        bulletPointList.mkString(" "),
        "If you’ve sent a tax return to us before, you can use your existing Unique Taxpayer Reference, or UTR, and register using form CWF1.",
        "If not, you’ll need to create a Government Gateway account on GOV.UK so that you can automatically register for Self Assessment. You’ll receive a letter with your UTR within 10 days, and another letter a few days later with a code to activate your online service.",
        "Partnerships must be registered by a nominated partner – but each partner needs to register individually as well, using the partnership’s UTR.",
        "If you’re not self-employed but need to send us a tax return, you can use your existing UTR and online account to submit it.",
        "If you haven’t got an existing UTR, you’ll need to register online by the fifth of October following the end of the tax year.",
        "For more information about registering for Self Assessment go to GOV.UK"
      )

      val doc = asDocument(createView())
      val contentDiv = doc
        .getElementById("registering-for-sa-transcript-content")
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
