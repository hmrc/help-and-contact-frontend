package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import controllers.actions._
import config.FrontendAppConfig
import views.html.saEvidence

import scala.concurrent.Future

class SaEvidenceController @Inject()(appConfig: FrontendAppConfig,
                                          override val messagesApi: MessagesApi,
                                          authenticate: AuthAction,
                                          serviceInfo: ServiceInfoAction ) extends FrontendController with I18nSupport {

  def onPageLoad = (authenticate andThen serviceInfo) {
    implicit request =>
      Ok(saEvidence(appConfig)(request.serviceInfoContent))
  }
}
