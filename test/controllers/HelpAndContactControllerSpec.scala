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
import models.HelpCategory
import play.api.test.Helpers._
import play.twirl.api.HtmlFormat
import views.html.sa._
import views.html.sa.register_deregister
import views.html.vat.{payments_and_deadlines, questions_about_vat}

class HelpAndContactControllerSpec extends ControllerSpecBase {

  def pageRouter(helpCategory: HelpCategory, page: String, view: () => HtmlFormat.Appendable) = {
    "HelpAndContactController onPageLoad" must {
      s"display the correct $helpCategory view for /$page" in {
        val result = controller().onPageLoad(helpCategory, page).apply(fakeRequest)
        status(result) mustBe OK
        contentAsString(result) mustBe view().toString
      }
    }
  }

  def controller() =
    new HelpAndContactController(frontendAppConfig, messagesApi, FakeAuthAction, FakeServiceInfoAction, injector.instanceOf[ErrorHandler])

  "HelpAndContact Controller" must {

    "return 404 for a page that does not exist" in {
      HelpCategory.values.foreach { category =>
        val result = controller().onPageLoad(category, "abcdefgh").apply(fakeRequest)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  behave like pageRouter(
    HelpCategory.VAT,
    "how-to-pay",
    () => payments_and_deadlines(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.SelfAssessment,
    "register-or-deregister",
    () => register_deregister(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.VAT,
    "questions",
    () => questions_about_vat(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )

  behave like pageRouter(
    HelpCategory.SelfAssessment,
    "how-to-pay",
    () => how_to_pay_self_assessment(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)
  )
}
