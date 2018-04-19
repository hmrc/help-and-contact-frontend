package views.self_assessment

import play.twirl.api.HtmlFormat
import views.behaviours.ViewBehaviours

class SaEvidenceViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "saEvidence"

  def createView = () => saEvidence(frontendAppConfig)(HtmlFormat.empty)(fakeRequest, messages)

  "SaEvidence view" must {
    behave like normalPage(createView, messageKeyPrefix)
  }
}
