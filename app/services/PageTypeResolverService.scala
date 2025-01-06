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

import models.PageType
import models.PageType.HelpWithBTA
import play.api.i18n.Messages
import play.twirl.api.{Html, HtmlFormat}

import javax.inject.Inject

class PageTypeResolverService {
  def resolve(pageType: PageType)(implicit messages: Messages): Html = pageType match {
    case PageType.HelpWithBTA => views.html.help_with_your_bta()(messages)
    case PageType.ChangeContactAndAccountDetails => views.html.change_contact_and_account_details()(messages)
    case PageType.HowToAddTax => views.html.how_to_add_tax()(messages)
    case PageType.ViewOrCorrectYourSubmissions => views.html.epaye_view_or_correct_submissions()(messages)
  }
}
