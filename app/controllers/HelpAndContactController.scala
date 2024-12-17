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

package controllers

import config.FrontendAppConfig
import controllers.actions._
import handlers.ErrorHandler

import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import utils.LoggingUtil
import views.html.help_and_contact

import scala.concurrent.ExecutionContext
import models.PageType
import services.{PageTypeResolverService}

class HelpAndContactController @Inject()(
                                          appConfig: FrontendAppConfig,
                                          help_and_contact: help_and_contact,
                                          override val messagesApi: MessagesApi,
                                          authenticate: AuthAction,
                                          serviceInfo: ServiceInfoAction,
                                          viewResolver: PageTypeResolverService,
                                          errorHandler: ErrorHandler,
                                          override val controllerComponents: MessagesControllerComponents
                                        ) extends FrontendController(controllerComponents)
  with I18nSupport with LoggingUtil {

  def mainPage: Action[AnyContent] = renderPage(PageType.HelpWithBTA.name)

  def renderPage(pageType: String): Action[AnyContent] = (authenticate andThen serviceInfo) { implicit request =>
    PageType.withName(pageType) match {
      case Some(validPageType) =>
        val dynamicContent: Html = viewResolver.resolve(validPageType)

        Ok(help_and_contact(appConfig)(
          request.serviceInfoContent,
          dynamicContent,
          validPageType.name,
          PageType.values
        ))
      case None =>
        NotFound(errorHandler.notFoundTemplate)
    }
  }
}