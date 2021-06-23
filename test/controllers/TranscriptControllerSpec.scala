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

package controllers

import play.api.test.Helpers._
import play.twirl.api.HtmlFormat
import views.html.transcripts._

class TranscriptControllerSpec extends ControllerSpecBase {

  lazy val SUT: TranscriptController = inject[TranscriptController]

  def pageRouter(videoTitle: String, view: () => HtmlFormat.Appendable) =
    "TranscriptController onPageLoad" must {
      s"display the correct view for /$videoTitle" in {
        val result = SUT.onPageLoad(videoTitle).apply(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe view().toString
      }
    }

  "TranscriptController Controller" must {

    "return 404 for a page that does not exist" in {
      val result = SUT.onPageLoad("abcdefgh").apply(fakeRequest)
      status(result) mustBe NOT_FOUND
    }
  }

  behave like pageRouter(
    "viewing-your-self-assessment-calculation",
    () =>
      inject[viewing_your_self_assessment_calculation].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "paying-your-self-assessment-tax-bill",
    () => inject[paying_your_self_assessment_tax_bill].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "budgeting-your-self-assessment-tax-bill",
    () =>
      inject[budgeting_your_self_assessment_tax_bill].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "why-sent-tax-return",
    () => inject[why_sent_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "your-first-tax-return",
    () => inject[your_first_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "tailor-your-tax-return",
    () => inject[tailor_your_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "your-self-employed-tax-return",
    () => inject[your_self_employed_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "your-income-from-property-tax-return",
    () => inject[your_income_from_property_tax_return].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "expenses-if-you-are-self-employed",
    () => inject[expenses_if_you_are_self_employed].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "calculating-motoring-expenses",
    () => inject[calculating_motoring_expenses].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )

  behave like pageRouter(
    "registering-for-self-assessment",
    () => inject[registering_for_self_assessment].apply(frontendAppConfig)(Some(HtmlFormat.empty))(fakeRequest, messages)
  )
}
