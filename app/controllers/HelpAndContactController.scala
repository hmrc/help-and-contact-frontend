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

import javax.inject.Inject
import config.FrontendAppConfig
import controllers.actions._
import handlers.ErrorHandler
import models.HelpCategory
import models.HelpCategory._
import models.requests.ServiceInfoRequest
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.ct._
import views.html.epaye._
import views.html.help_and_contact
import views.html.sa._
import views.html.vat._

import scala.concurrent.ExecutionContext

class HelpAndContactController @Inject()(
                                          appConfig: FrontendAppConfig,
                                          contact_hmrc_about_ct: contact_hmrc_about_ct,
                                          how_to_pay_corporation_tax: how_to_pay_corporation_tax,
                                          register_or_deregister_corporation_tax: register_or_deregister_corporation_tax,
                                          paye_and_cis_refunds: paye_and_cis_refunds,
                                          view_check_correct_submissions: view_check_correct_submissions,
                                          help_and_contact: help_and_contact,
                                          sa_evidence: sa_evidence,
                                          expenses: expenses,
                                          help_with_your_self_assessment_tax_return: help_with_your_self_assessment_tax_return,
                                          register_or_stopping: register_or_stopping,
                                          payments_and_deadlines: payments_and_deadlines,
                                          register_or_deregister: register_or_deregister,
                                          payment_and_penalties: payment_and_penalties,
                                          override val messagesApi: MessagesApi,
                                          authenticate: AuthAction,
                                          serviceInfo: ServiceInfoAction,
                                          override val controllerComponents: MessagesControllerComponents,
                                          errorHandler: ErrorHandler
                                        ) extends FrontendController(controllerComponents)
  with I18nSupport {
  implicit val ec: ExecutionContext                    = controllerComponents.executionContext

  val youtubeFeatureSwitch = appConfig.youtubeLinksEnabled
  def mainPage = (authenticate andThen serviceInfo) { implicit request =>
    Ok(help_and_contact(appConfig)(request.serviceInfoContent))
  }

  def onPageLoad(category: HelpCategory, page: String) = (authenticate andThen serviceInfo) { implicit request =>
    category match {
      case CorporationTax => corporationTax(page)
      case Epaye => ePaye(page)
      case SelfAssessment => selfAssessment(page)
      case VAT => vat(page)
    }
  }

  private def corporationTax(page: String)(implicit request: ServiceInfoRequest[AnyContent]) =
    page match {
      case "contact-hmrc" => Ok(contact_hmrc_about_ct(appConfig)(request.serviceInfoContent))
      case "how-to-pay" => Ok(how_to_pay_corporation_tax(appConfig)(request.serviceInfoContent))
      case "register-or-tell-hmrc-you-are-no-longer-trading" =>
        Ok(register_or_deregister_corporation_tax(appConfig)(request.serviceInfoContent))
      case _ => NotFound(errorHandler.notFoundTemplate)
    }

  private def ePaye(page: String)(implicit request: ServiceInfoRequest[AnyContent]) =
    page match {
      case "get-started" => MovedPermanently("/business-account/epaye/get-started")
      case "remove" => MovedPermanently("/business-account/epaye/remove")
      case "refunds" => Ok(paye_and_cis_refunds(appConfig)(request.serviceInfoContent))
      case "view-check-correct-submissions" =>
        Ok(view_check_correct_submissions(appConfig, request.request.email)(request.serviceInfoContent))
      case "change-employee-circumstances" => MovedPermanently("/business-account/epaye/change-employee-circumstances")
      case "check-submissions" => MovedPermanently("/business-account/help/epaye/view-check-correct-submissions")
      case "latency" => MovedPermanently("/business-account/help/epaye/view-check-correct-submissions")
      case "paye-refund" => MovedPermanently("/business-account/help/epaye/refunds")
      case "" => MovedPermanently("/business-account/help")
      case _ => NotFound(errorHandler.notFoundTemplate)
    }

  private def selfAssessment(page: String)(implicit request: ServiceInfoRequest[AnyContent]) =
    page match {
      case "evidence-of-income" => {
        Ok(
          sa_evidence(appConfig,
            request.request.saUtr.isDefined,
            appConfig.getBusinessAccountUrl("selfAssessmentBase"))(
            request.serviceInfoContent
          )
        )
      }
      case "expenses" =>  {
        Ok(expenses(appConfig)(request.serviceInfoContent))
      }
      case "help-with-return" => {
        Ok(help_with_your_self_assessment_tax_return(appConfig, request.request.saUtr)(request.serviceInfoContent))
      }
      case "payment-and-penalties" => {
        Ok(payment_and_penalties(appConfig, request.request.saUtr)(request.serviceInfoContent))
      }
      case "how-to-pay" => {
        Redirect(controllers.routes.HelpAndContactController.onPageLoad(SelfAssessment, "payment-and-penalties"))
      }
      case "register-or-stopping" => Ok(register_or_stopping(appConfig)(request.serviceInfoContent))
      case _ => NotFound(errorHandler.notFoundTemplate)
    }

  private def vat(page: String)(implicit request: ServiceInfoRequest[AnyContent]) =
    page match {
      case "how-to-pay" => Ok(payments_and_deadlines(appConfig)(request.serviceInfoContent))
      case "register-or-deregister" => Ok(register_or_deregister(appConfig)(request.serviceInfoContent))
      case _ => NotFound(errorHandler.notFoundTemplate)
    }

}
