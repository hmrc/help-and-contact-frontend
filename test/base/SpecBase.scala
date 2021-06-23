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

package base

import config.FrontendAppConfig
import controllers.actions.AuthAction
import handlers.ErrorHandler
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice._
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.AnyContent
import play.api.test.{FakeRequest, Injecting}
import views.html.ct.{contact_hmrc_about_ct, how_to_pay_corporation_tax, register_or_deregister_corporation_tax}
import views.html.epaye.{paye_and_cis_refunds, view_check_correct_submissions}
import views.html.help_and_contact
import views.html.sa._
import views.html.vat.{payments_and_deadlines, register_or_deregister}

trait SpecBase extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  def frontendAppConfig: FrontendAppConfig = inject[FrontendAppConfig]

  def messagesApi: MessagesApi = inject[MessagesApi]

  def fakeRequest: FakeRequest[AnyContent] = FakeRequest("", "")

  def messages: Messages = messagesApi.preferred(fakeRequest)

  def contactHMRCAboutCt: contact_hmrc_about_ct = inject[contact_hmrc_about_ct]

  def howToPayCorporationTax: how_to_pay_corporation_tax = inject[how_to_pay_corporation_tax]

  def registerOrDeregisterCorporationTax: register_or_deregister_corporation_tax = inject[register_or_deregister_corporation_tax]

  def payeAndCisRefunds: paye_and_cis_refunds = inject[paye_and_cis_refunds]

  def viewCheckCorrectSubmissions: view_check_correct_submissions = inject[view_check_correct_submissions]

  def helpAndContact: help_and_contact = inject[help_and_contact]

  def saEvidence: sa_evidence = inject[sa_evidence]

  def expenses: expenses = inject[expenses]

  def helpWithYourSelfAssessmentTaxReturn: help_with_your_self_assessment_tax_return = inject[help_with_your_self_assessment_tax_return]

  def paymentsAndDeadlines: payments_and_deadlines = inject[payments_and_deadlines]

  def registerOrDeregister: register_or_deregister = inject[register_or_deregister]

  def errorHandler: ErrorHandler = inject[ErrorHandler]

  def authenticate: AuthAction = inject[AuthAction]

  def registerOrStopping: register_or_stopping = inject[register_or_stopping]


}
