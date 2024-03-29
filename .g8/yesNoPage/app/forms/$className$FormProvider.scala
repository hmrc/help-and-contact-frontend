package forms

import javax.inject.Inject
import play.api.data.Form

class $className$FormProvider @Inject() extends FormErrorHelper with Mappings {

  def apply(): Form[Boolean] =
    Form(
      "value" -> boolean("$className;format="decap"$.error.required")
    )
}
