package controllers

import play.api.data.Form
import play.api.mvc.Call
import play.twirl.api.HtmlFormat;format="decap"$

class $className$ControllerSpec extends ControllerSpecBase {

  def onwardRoute: Call = routes.HelpAndContactController.mainPage()

  val formProvider = new $className$FormProvider()
  val form = formProvider()

  def controller(dataRetrievalAction: DataRetrievalAction = getEmptyCacheMap) =
    new $className$Controller(frontendAppConfig, messagesApi, FakeDataCacheConnector, new FakeNavigator(desiredRoute = onwardRoute), FakeAuthAction,
      FakeServiceInfoAction, formProvider)

  def viewAsString(form: Form[_] = form) = $className;format="decap"$(frontendAppConfig, form)(HtmlFormat.empty)(fakeRequest, messages).toString

  "$className$ Controller" must {

    "return OK and the correct view for a GET" in {
      val result = controller().onPageLoad()(fakeRequest)

      status(result) mustBe OK
      contentAsString(result) mustBe viewAsString()
    }

    "redirect to the next page when valid data is submitted" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", $className$.options.head.value))

      val result = controller().onSubmit()(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }

    "return a Bad Request and errors when invalid data is submitted" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
      val boundForm = form.bind(Map("value" -> "invalid value"))

      val result = controller().onSubmit()(postRequest)

      status(result) mustBe BAD_REQUEST
      contentAsString(result) mustBe viewAsString(boundForm)
    }

    "return OK if no existing data is found" in {
      val result = controller(dontGetAnyData).onPageLoad()(fakeRequest)

      status(result) mustBe OK
    }

    "redirect to next page when valid data is submitted and no existing data is found" in {
      val postRequest = fakeRequest.withFormUrlEncodedBody(("value", ($className$.options.head.value)))
      val result = controller(dontGetAnyData).onSubmit()(postRequest)

      status(result) mustBe SEE_OTHER
      redirectLocation(result) mustBe Some(onwardRoute.url)
    }
  }
}
