package controllers

import config.FrontendAppConfig
import controllers.actions._
import javax.inject.Inject
import models.Mode
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import utils.{Enumerable, UserAnswers};format="decap"$

import scala.concurrent.Future

class $className$Controller @Inject()(
                                        appConfig: FrontendAppConfig,
                                        override val messagesApi: MessagesApi,
                                        dataCacheConnector: DataCacheConnector,
                                        navigator: Navigator,
                                        authenticate: AuthAction,
                                        getData: DataRetrievalAction,
                                        requireData: DataRequiredAction,
                                        formProvider: $className$FormProvider) extends FrontendController with I18nSupport with Enumerable.Implicits {

  val form = formProvider()

  def onPageLoad(mode: Mode) = (authenticate andThen getData andThen requireData) {
    implicit request =>
      val preparedForm = request.userAnswers.$className;format="decap"$ match {
        case None => form
        case Some(value) => form.fill(value)
      }
      Ok($className;format="decap"$(appConfig, preparedForm, mode))
  }

  def onSubmit(mode: Mode) = (authenticate andThen getData andThen requireData).async {
    implicit request =>
      form.bindFromRequest().fold(
        (formWithErrors: Form[_]) =>
          Future.successful(BadRequest($className;format="decap"$(appConfig, formWithErrors, mode))),
        (value) =>
          dataCacheConnector.save[$className$](request.externalId, $className$Id.toString, value).map(cacheMap =>
            Redirect(navigator.nextPage($className$Id, mode)(new UserAnswers(cacheMap))))
      )
  }
}
