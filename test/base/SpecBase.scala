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

package base

import config.FrontendAppConfig
import controllers.actions.AuthAction
import handlers.ErrorHandler
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice._
import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.AnyContent
import play.api.test.{FakeRequest, Injecting}
import views.html.help_and_contact


trait SpecBase extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  def frontendAppConfig: FrontendAppConfig = inject[FrontendAppConfig]

  def messagesApi: MessagesApi = inject[MessagesApi]

  def fakeRequest: FakeRequest[AnyContent] = FakeRequest("", "")

  def messages: Messages = messagesApi.preferred(fakeRequest)

  def helpAndContact: help_and_contact = inject[help_and_contact]

  def errorHandler: ErrorHandler = inject[ErrorHandler]

  def authenticate: AuthAction = inject[AuthAction]


}
