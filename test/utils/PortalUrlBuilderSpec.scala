/*
 * Copyright 2018 HM Revenue & Customs
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

package utils

import base.SpecBase
import play.api.mvc.Cookie
import uk.gov.hmrc.domain.{CtUtr, SaUtrGenerator}

import scala.util.Random

class PortalUrlBuilderSpec extends SpecBase {

  object PortalUrlBuilder extends PortalUrlBuilder


  val fakeRequestWithWelsh = fakeRequest.withCookies(Cookie("PLAY_LANG", "cy"))

  "build portal url" when {

    "there is <utr>" should {
      val utr = new SaUtrGenerator(new Random).nextSaUtr
      "return the provided url with the current users UTR" in {
        PortalUrlBuilder.buildPortalUrl("http://testurl/<utr>/")(Some(utr))(fakeRequest) mustBe s"http://testurl/$utr/?lang=eng"
      }
    }

    "the user is in english" should {
      "append ?lang=eng to given url" in {
        PortalUrlBuilder.buildPortalUrl("http://testurl")(None)(fakeRequest) mustBe "http://testurl?lang=eng"
      }
    }

    "the user is in welsh" should {
      "append ?lang=cym to given url" in {
        PortalUrlBuilder.buildPortalUrl("http://testurl")(None)(fakeRequestWithWelsh) mustBe "http://testurl?lang=cym"
      }
    }
  }
}
