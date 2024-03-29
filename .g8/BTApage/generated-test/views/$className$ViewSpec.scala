package views

import play.twirl.api.HtmlFormat;format="decap"$

class $className$ViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"

  def createView = () => $className;format="decap"$(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

  "$className$ view" must {
    behave like normalPage(createView, messageKeyPrefix)
  }
}
