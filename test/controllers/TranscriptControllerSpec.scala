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

package controllers

import controllers.actions._
import handlers.ErrorHandler
import play.api.test.Helpers._
import play.twirl.api.HtmlFormat
import views.html.transcripts._

class TranscriptControllerSpec extends ControllerSpecBase {

  def pageRouter(videoTitle: String, view: () => HtmlFormat.Appendable) = {
    "TranscriptController onPageLoad" must {
      s"display the correct view for /$videoTitle" in {
        val result = controller().onPageLoad(videoTitle).apply(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe view().toString
      }
    }
  }

  def controller() =
    new TranscriptController(frontendAppConfig, messagesApi, FakeAuthAction, FakeServiceInfoAction, injector.instanceOf[ErrorHandler])

  "TranscriptController Controller" must {

    "return 404 for a page that does not exist" in {
        val result = controller().onPageLoad("abcdefgh").apply(fakeRequest)
        status(result) mustBe NOT_FOUND
    }
  }

  behave like pageRouter(
    "viewing-your-self-assessment-calculation",
    () => viewing_your_self_assessment_calculation(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    "paying-your-self-assessment-tax-bill",
    () => paying_your_self_assessment_tax_bill(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    "budgeting-your-self-assessment-tax-bill",
    () => budgeting_your_self_assessment_tax_bill(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    "self-assessment-penalties",
    () => self_assessment_penalties(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )
}


