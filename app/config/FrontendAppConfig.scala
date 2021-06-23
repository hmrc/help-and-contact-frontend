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

package config

import javax.inject.{Inject, Singleton}
import controllers.routes
import play.api.i18n.Lang
import play.api.mvc.{Call, Request}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.play.language.LanguageUtils
import models.SaUtr
import utils.PortalUrlBuilder
import uk.gov.hmrc.time.TaxYear
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, Language}


@Singleton
class FrontendAppConfig @Inject()(servicesConfig: ServicesConfig,
                                  override val languageUtils: LanguageUtils)
    extends PortalUrlBuilder {

  private def loadConfig(key: String): String = servicesConfig.getString(key)
  lazy val analyticsToken: String           = loadConfig(s"google-analytics.token")
  lazy val analyticsHost: String            = loadConfig(s"google-analytics.host")
  lazy val btaUrl: String                   = servicesConfig.baseUrl("business-tax-account")
  lazy val loginUrl: String                 = loadConfig("urls.login")
  lazy val loginContinueUrl: String         = loadConfig("urls.loginContinue")
  lazy val requestCorporationTaxUTR: String = loadConfig("urls.requestCorporationTaxUTR")
  lazy val googleTagManagerId: String       = loadConfig(s"google-tag-manager.id")
  lazy val appName: String                  = loadConfig("appName")

  private lazy val contactHost: String = servicesConfig.getString("contact-frontend.host")
  private val contactFormServiceIdentifier: String = "helpandcontactfrontend"
  lazy val reportAProblemPartialUrl: String = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  lazy val reportAProblemNonJSUrl: String = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"

  def getGovUrl(key: String): String = loadConfig(s"urls.external.govuk.$key")

  def getYoutubeVideoId(key: String): String = loadConfig(s"urls.external.youtube.$key")

  private lazy val businessAccountHost: String = servicesConfig.getString("urls.business-account.host")

  private lazy val addTaxHost: String = servicesConfig.getString("urls.add-tax.host")

  def getBusinessAccountUrl(key: String): String = businessAccountHost + loadConfig(s"urls.business-account.$key")

  def getAddTaxUrl(key: String): String = addTaxHost + loadConfig(s"urls.add-tax.$key")

  private lazy val dfsHost: String = servicesConfig.getString("urls.digital-forms-service.host")
  private lazy val dfsBase: String = servicesConfig.getString("urls.digital-forms-service.base")
  private lazy val dfsSuffix: String = servicesConfig.getString("urls.digital-forms-service.suffix")

  def getDfsFormUrl(formId: String): String = s"$dfsHost$dfsBase${servicesConfig.getString(s"urls.digital-forms-service.formTypeRef.$formId")}$dfsSuffix"

  lazy val languageTranslationEnabled: Boolean = servicesConfig.getBoolean("microservice.services.features.welsh-translation")

  lazy val youtubeLinksEnabled: Boolean = servicesConfig.getBoolean("microservice.services.features.youtube-links")

  def languageMap: Map[String, Lang] = Map("english" -> Lang("en"), "cymraeg" -> Lang("cy"))

  def routeToSwitchLanguage(lang: String): Call = routes.LanguageSwitchController.switchToLanguage(lang)

  private lazy val portalHost: String = loadConfig("portal.host")

  def getPortalUrl(key: String, saUtr: Option[SaUtr] = None, taxYearCode: Option[String] = None)(implicit request: Request[_]): String =
    buildPortalUrl(portalHost + loadConfig(s"urls.portal.$key"))(saUtr, taxYearCode)

  def sessionTimeoutInSeconds: Long  = 900
  def sessionCountdownInSeconds: Int = 60

  lazy val taxYearStart: Int = TaxYear.current.startYear
  lazy val taxYearEnd: Int = TaxYear.current.finishYear


  lazy val taxYearBegin: String = taxYearStart.toString
  lazy val taxYearFinish: String = taxYearEnd.toString
  lazy val taxYearPrevious: String = (taxYearStart - 1).toString
  lazy val taxYearPrevious2: String = (taxYearStart - 2).toString
  lazy val taxYearNext: String = (taxYearStart + 1).toString
  lazy val previousTaxYearCode: String = TaxYearCode(taxYearEnd -1).toString

  def fileAReturn(key: String, sa: SaUtr)(implicit request: Request[_]): String = {
    getPortalUrl(key, Some(sa), Some(TaxYearCode(taxYearEnd - 1).toString))
  }

  def languageLinks: Seq[(Language, String)] = {
    Seq(
      (En, routes.LanguageSwitchController.switchToLanguage("english").url),
      (Cy, routes.LanguageSwitchController.switchToLanguage("cymraeg").url)
    )
  }
}
