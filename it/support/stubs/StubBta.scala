package support.stubs

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, get, urlEqualTo}
import com.github.tomakehurst.wiremock.http.Fault

object StubBta {

  def mockGetServiceInfo()(implicit wiremock: WireMockServer): Unit =
    wiremock.stubFor(get(urlEqualTo("/business-account/partial/service-info"))
      .willReturn(aResponse()
        .withBody("""<div id="service-info-partial"></div>""")
      )
    )

  def mockGetNavLinks()(implicit wiremock: WireMockServer): Unit =
    wiremock.stubFor(get(urlEqualTo("/business-account/partial/nav-links"))
      .willReturn(aResponse()
        .withBody("""<div id="service-info-partial"></div>""")
      )
    )

  def mockGetServiceInfoFailure()(implicit wiremock: WireMockServer): Unit =
    wiremock.stubFor(get(urlEqualTo("/business-account/partial/service-info"))
      .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK))
    )

}
