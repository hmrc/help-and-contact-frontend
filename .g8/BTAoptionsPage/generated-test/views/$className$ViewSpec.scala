package views

import play.api.data.Form
import play.twirl.api.HtmlFormat;format="decap"$

class $className$ViewSpec extends ViewBehaviours {

  val messageKeyPrefix = "$className;format="decap"$"

  val form = new $className$FormProvider()()

  val serviceInfoContent = HtmlFormat.empty

  def createView = () => $className;format="decap"$(frontendAppConfig, form)(serviceInfoContent)(fakeRequest, messages)

  def createViewUsingForm = (form: Form[_]) => $className;format="decap"$(frontendAppConfig, form)(serviceInfoContent)(fakeRequest, messages)

  "$className$ view" must {
    behave like normalPage(createView, messageKeyPrefix)
  }

  "$className$ view" when {
    "rendered" must {
      "contain radio buttons for the value" in {
        val doc = asDocument(createViewUsingForm(form))
        for (option <- $className$.options) {
          assertContainsRadioButton(doc, option.id, "value", option.value, false)
        }
      }
    }

    for(option <- $className$.options) {
      s"rendered with a value of '\${option.value}'" must {
        s"have the '\${option.value}' radio button selected" in {
          val doc = asDocument(createViewUsingForm(form.bind(Map("value" -> s"\${option.value}"))))
          assertContainsRadioButton(doc, option.id, "value", option.value, true)

          for(unselectedOption <- $className$.options.filterNot(o => o == option)) {
            assertContainsRadioButton(doc, unselectedOption.id, "value", unselectedOption.value, false)
          }
        }
      }
    }
  }
}
