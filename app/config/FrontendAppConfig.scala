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

package config

import com.typesafe.config.{ConfigList, ConfigRenderOptions}
import controllers.routes

import javax.inject.{Inject, Singleton}
import models.{SaUtr, VatThreshold}
import play.api.Configuration
import play.api.i18n.Lang
import play.api.libs.json.Json
import play.api.mvc.Request
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, Language}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.play.language.LanguageUtils
import uk.gov.hmrc.time.TaxYear
import utils.PortalUrlBuilder


@Singleton
class FrontendAppConfig @Inject()(servicesConfig: ServicesConfig,
                                  configuration: Configuration,
                                  override val languageUtils: LanguageUtils)
    extends PortalUrlBuilder {

  private def loadConfig(key: String): String = servicesConfig.getString(key)
  lazy val btaUrl: String                   = servicesConfig.baseUrl("business-tax-account")
  lazy val loginUrl: String                 = loadConfig("urls.login")
  lazy val loginContinueUrl: String         = loadConfig("urls.loginContinue")
  lazy val appName: String                  = loadConfig("appName")

  def getGovUrl(key: String): String = loadConfig(s"urls.external.govuk.$key")

  private lazy val businessAccountHost: String = servicesConfig.getString("urls.business-account.host")

  private lazy val tarHost: String = servicesConfig.getString("tax-account-router-frontend.host")

  private lazy val addTaxHost: String = servicesConfig.getString("urls.add-tax.host")

  private lazy val userProfileRedirectHost: String = servicesConfig.getString("urls.user-profile-redirect.host")

  def getBusinessAccountUrl(key: String): String = businessAccountHost + loadConfig(s"urls.business-account.$key")

  def getTarUrl: String = tarHost + loadConfig(s"tax-account-router-frontend.url")

  def getAddTaxUrl(key: String): String = addTaxHost + loadConfig(s"urls.add-tax.$key")

  def getUserProfileRedirect(key: String): String = userProfileRedirectHost + loadConfig(s"urls.user-profile-redirect.$key")

  private lazy val dfsHost: String = servicesConfig.getString("urls.digital-forms-service.host")
  private lazy val dfsBase: String = servicesConfig.getString("urls.digital-forms-service.base")
  private lazy val dfsSuffix: String = servicesConfig.getString("urls.digital-forms-service.suffix")

  def getDfsFormUrl(formId: String): String = s"$dfsHost$dfsBase${servicesConfig.getString(s"urls.digital-forms-service.formTypeRef.$formId")}$dfsSuffix"

  lazy val languageTranslationEnabled: Boolean = servicesConfig.getBoolean("microservice.services.features.welsh-translation")


  private lazy val portalHost: String = loadConfig("portal.host")

  def getPortalUrl(key: String, saUtr: Option[SaUtr] = None, taxYearCode: Option[String] = None)(implicit request: Request[_]): String =
    buildPortalUrl(portalHost + loadConfig(s"urls.portal.$key"))(saUtr, taxYearCode)

  def sessionTimeoutInSeconds: Int  = 900
  def sessionCountdownInSeconds: Int = 60

  lazy val taxYearStart: Int = TaxYear.current.startYear
  lazy val taxYearEnd: Int = TaxYear.current.finishYear


  lazy val taxYearBegin: String = taxYearStart.toString
  lazy val taxYearPrevious: String = (taxYearStart - 1).toString
  lazy val taxYearPrevious2: String = (taxYearStart - 2).toString
  lazy val taxYearNext: String = (taxYearStart + 1).toString
  lazy val previousTaxYearCode: String = TaxYearCode(taxYearEnd -1).toString

  def fileAReturn(key: String, sa: SaUtr)(implicit request: Request[_]): String = {
    getPortalUrl(key, Some(sa), Some(TaxYearCode(taxYearEnd - 1).toString))
  }

  def languageLinks: Seq[(Language, String)] = {
    Seq(
      (En, routes.LanguageController.switchToEnglish.url),
      (Cy, routes.LanguageController.switchToWelsh.url)
    )
  }

  def thresholdString: String = configuration.get[ConfigList]("vat-threshold").render(ConfigRenderOptions.concise())
  lazy val thresholds: Seq[VatThreshold] = Json.parse(thresholdString).as[List[VatThreshold]]

}
