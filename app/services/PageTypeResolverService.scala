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

package services

import config.FrontendAppConfig
import models.PageType
import models.requests.ServiceInfoRequest
import play.api.i18n.Messages
import play.api.mvc.AnyContent
import play.twirl.api.{Html, HtmlFormat}
import views.html._
import views.html.sa._
import views.html.general._
import views.html.vat._
import views.html.ct._
import views.html.epaye._

import javax.inject.Inject

class PageTypeResolverService @Inject()(
                                         appConfig: FrontendAppConfig
                                       ){
  def resolve(pageType: PageType)(implicit request: ServiceInfoRequest[AnyContent], messages: Messages): Html = pageType match {
    case p@PageType.HelpWithBTA => help_with_your_bta(p.name, appConfig)
    case p@PageType.RegisteringOrStopping => registering_or_stopping(p.name, appConfig)(messages)
    case p@PageType.PaymentsAndPenalties => payments_and_penalties(p.name, appConfig, request.request.saUtr)(request.serviceInfoContent)
    case p@PageType.HelpWithSATaxReturn => help_with_sa_tax_return(p.name, request.request.saUtr, appConfig)
    case p@PageType.GetEvidenceOfIncome => get_evidence_of_income(p.name, appConfig, request.request.saUtr.isDefined)(request.serviceInfoContent)
    case p@PageType.Expenses => expenses(p.name)
    case PageType.SignUpForMTD => HtmlFormat.empty
    case p@PageType.ChangeContactAndAccountDetails => change_contact_and_account_details(p.name, appConfig)(messages)
    case p@PageType.HowToAddTax => how_to_add_tax(p.name)(messages)
    case p@PageType.RegisterOrDeregisterVAT => register_or_deregister(p.name)(messages)
    case p@PageType.HowToPayVatAndDeadlines => how_to_pay_vat_and_deadlines(p.name)(messages)
    case p@PageType.CorrectingErrorsOnReturns => correcting_errors_on_returns(p.name)(messages)
    case p@PageType.GetStarted => epaye_get_started(p.name)(messages)
    case p@PageType.ViewOrCorrectYourSubmissions => epaye_view_or_correct_submissions(p.name, request.request.email)(messages)
    case p@PageType.PayeCisRefunds => paye_cis_refunds(p.name)(messages)
    case p@PageType.PayeChangeCircumstance => paye_change_circumstance(p.name)(messages)
    case p@PageType.PayeStopEmployer => paye_stop_employer(p.name)(messages)
    case p@PageType.RegisterAddCT => ctax_register_add(p.name)(messages)
    case p@PageType.HowToPayCT => ctax_how_to_pay(p.name)(messages)
    case p@PageType.ClosingLimitedCompanyCT => ctax_closing_limited_company(p.name)(messages)
    case p@PageType.GetUtrCT => ctax_ask_utr_corporation_tax(p.name)(messages)
    case p@PageType.ContactHMRC => contact_hmrc(p.name)(messages)
  }
}
