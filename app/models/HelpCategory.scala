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

package models

import play.api.mvc.JavascriptLiteral
import utils.WithName

sealed trait HelpCategory
object HelpCategory {
  case object CorporationTax extends WithName("corporation_tax") with HelpCategory
  case object Epaye extends WithName("paye_for_employers") with HelpCategory
  case object SelfAssessment extends WithName("self_assessment") with HelpCategory
  case object VAT extends WithName("vat") with HelpCategory
  case object GEN extends WithName("contact_hmrc") with HelpCategory
  case object BTA extends WithName("bta") with HelpCategory


  // [IMPORTANT] Ensure rendering order.
  val values = Seq(
    BTA,
    SelfAssessment,
    VAT,
    Epaye,
    CorporationTax,
    GEN
  )

  implicit val jsLiteral: JavascriptLiteral[HelpCategory] = new JavascriptLiteral[HelpCategory] {
    override def to(value: HelpCategory): String = value match {
      case CorporationTax => "CorporationTax"
      case Epaye => "Epaye"
      case SelfAssessment => "SelfAssessment"
      case VAT => "VAT"
      case GEN => "GEN"
    }
  }
}