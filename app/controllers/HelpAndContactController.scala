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

import javax.inject.Inject

import config.FrontendAppConfig
import controllers.actions._
import handlers.ErrorHandler
import models.HelpCategory
import models.HelpCategory.{SelfAssessment, VAT}
import javax.inject.Inject

import models.HelpCategory
import models.HelpCategory.{SA, VAT}
import models.requests.ServiceInfoRequest
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.AnyContent
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.sa._
import views.html.vat.{payments_and_deadlines, questions_about_vat}
import uk.gov.hmrc.play.bootstrap.http.FrontendErrorHandler
import views.html.selfAssessment.register_Deregister
import views.html.vat.payments_and_deadlines

class HelpAndContactController @Inject()(appConfig: FrontendAppConfig,
                                         override val messagesApi: MessagesApi,
                                         authenticate: AuthAction,
                                         serviceInfo: ServiceInfoAction,
                                         errorHandler: ErrorHandler) extends FrontendController with I18nSupport {

  def onPageLoad(category: HelpCategory, page: String) = (authenticate andThen serviceInfo) {
    implicit request =>
      category match {
        case VAT => vat(page)
        case SelfAssessment => selfAssessment(page)
      }
  }

  private def vat(page: String)(implicit request: ServiceInfoRequest[AnyContent]) = {
    page match {
      case "how-to-pay" => Ok(payments_and_deadlines(appConfig)(request.serviceInfoContent))
      case "questions" => Ok(questions_about_vat(appConfig)(request.serviceInfoContent))
      case _ => NotFound(errorHandler.notFoundTemplate)
    }
  }

  private def selfAssessment(page: String)(implicit request: ServiceInfoRequest[AnyContent]) = {
    page match {
      case "how-to-pay" => Ok(how_to_pay_self_assessment(appConfig)(request.serviceInfoContent))
      case "register-or-deregister" => Ok(register_Deregister(appConfig)(request.serviceInfoContent))
      case _ => NotFound(errorHandler.notFoundTemplate)
    }
  }
}
