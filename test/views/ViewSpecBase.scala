/*
 * Copyright 2024 HM Revenue & Customs
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

package views

import base.SpecBase
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import play.twirl.api.Html
import play.twirl.api.TwirlHelperImports.twirlJavaCollectionToScala


trait ViewSpecBase extends SpecBase {

  def asDocument(html: Html): Document = Jsoup.parse(html.toString())

  def assertEqualsMessage(doc: Document, cssSelector: String, expectedMessageKey: String) =
    assertEqualsValue(doc, cssSelector, messages(expectedMessageKey))

  def assertEqualsValue(doc: Document, cssSelector: String, expectedValue: String) = {
    val elements = doc.select(cssSelector)

    if (elements.isEmpty) throw new IllegalArgumentException(s"CSS Selector $cssSelector wasn't rendered.")

    //<p> HTML elements are rendered out with a carriage return on some pages, so discount for comparison
    assert(elements.first().html().replace("\n", "") == expectedValue)
  }

  def assertPageTitleEqualsMessage(doc: Document, expectedMessageKey: String, args: Any*) = {
    val headers = doc.getElementsByTag("h1")
    headers.size mustBe 1
    headers.first.text.replaceAll("\u00a0", " ") mustBe messages(expectedMessageKey, args: _*).replaceAll("&nbsp;", " ")
  }

  def assertContainsText(doc: Document, text: String) = assert(doc.toString.contains(text), "\n\ntext " + text + " was not rendered on the page.\n")

  def assertContainsMessages(doc: Document, expectedMessageKeys: String*) = {
    for (key <- expectedMessageKeys) assertContainsText(doc, messages(key))
  }

  def assertRenderedById(doc: Document, id: String) = {
    assert(doc.getElementById(id) != null, "\n\nElement " + id + " was not rendered on the page.\n")
  }

  def assertRenderedByClass(doc: Document, classes: String) = {
    assert(doc.getElementsByClass(classes) != null, "\n\nElement " + classes + " was not rendered on the page.\n")
  }

  def assertRenderedByTag(doc: Document, tag: String) = {
    assert(doc.getElementsByTag(tag).first() != null, "\n\nElement " + tag + " was not rendered on the page.\n")
  }

  def assertNotRenderedById(doc: Document, id: String) = {
    assert(doc.getElementById(id) == null, "\n\nElement " + id + " was rendered on the page.\n")
  }

  def assertRenderedByCssSelector(doc: Document, cssSelector: String) = {
    assert(!doc.select(cssSelector).isEmpty, "Element " + cssSelector + " was not rendered on the page.")
  }

  def assertNotRenderedByCssSelector(doc: Document, cssSelector: String) = {
    assert(doc.select(cssSelector).isEmpty, "\n\nElement " + cssSelector + " was rendered on the page.\n")
  }

  def assertContainsLabel(doc: Document, forElement: String, expectedText: String, expectedHintText: Option[String] = None) = {
    val labels = doc.getElementsByAttributeValue("for", forElement)
    assert(labels.size == 1, s"\n\nLabel for $forElement was not rendered on the page.")
    val label = labels.first
    assert(label.text() == expectedText, s"\n\nLabel for $forElement was not $expectedText")

    if (expectedHintText.isDefined) {
      assert(label.getElementsByClass("form-hint").first.text == expectedHintText.get,
        s"\n\nLabel for $forElement did not contain hint text $expectedHintText")
    }
  }

  def assertElementHasClass(doc: Document, id: String, expectedClass: String) = {
    assert(doc.getElementById(id).hasClass(expectedClass), s"\n\nElement $id does not have class $expectedClass")
  }

  def assertContainsRadioButton(doc: Document, id: String, name: String, value: String, isChecked: Boolean) = {
    assertRenderedById(doc, id)
    val radio = doc.getElementById(id)
    assert(radio.attr("name") == name, s"\n\nElement $id does not have name $name")
    assert(radio.attr("value") == value, s"\n\nElement $id does not have value $value")
    isChecked match {
      case true => assert(radio.attr("checked") == "checked", s"\n\nElement $id is not checked")
      case _ => assert(!radio.hasAttr("checked") && radio.attr("checked") != "checked", s"\n\nElement $id is checked")
    }
  }

  def assertLinkById(doc: Document, linkId: String, expectedText: String, expectedUrl: String, expectedIsExternal: Boolean = false, expectedOpensInNewTab: Boolean = false, exactUrl: Boolean = true) {
    val link = doc.getElementById(linkId)
    assert(link.text() == expectedText, s"\n\n Link $linkId does not have text $expectedText")
    if(exactUrl == true){
      assert(link.attr("href") == expectedUrl, s"\n\n Link $linkId does not expectedUrl $expectedUrl")
    } else {
      assert(link.attr("href").equals(expectedUrl), s"\n\n Link $linkId does not contain expectedUrl $expectedUrl")
    }
    assert(link.attr("rel").equals("external") == expectedIsExternal, s"\n\n Link $linkId does not meet expectedIsExternal $expectedIsExternal")

    assert(link.attr("target").contains("_blank") == expectedOpensInNewTab, s"\n\n Link $linkId does not meet expectedOpensInNewTab $expectedOpensInNewTab")
  }

  def assertLinkByClass(doc: Document, linkClass: String, expectedText: String, expectedUrl: String, expectedIsExternal: Boolean = false, expectedOpensInNewTab: Boolean = false, exactUrl: Boolean = true) {
    val link = doc.getElementsByClass(linkClass)
    assert(link.text().contains(expectedText), s"\n\n Link $linkClass does not have text $expectedText")
    assert(link.eachAttr("href").contains(expectedUrl), s"\n\n Link $linkClass does not contain expectedUrl $expectedUrl")
    assert(link.attr("rel").contains("external") == expectedIsExternal, s"\n\n Link $linkClass does not meet expectedIsExternal $expectedIsExternal")
    assert(link.attr("target").contains("_blank") == expectedOpensInNewTab, s"\n\n Link $linkClass does not meet expectedOpensInNewTab")
  }

  def assertLinkByDocId(doc: Document, linkId: String, expectedText: String, expectedUrl: String, expectedGAEvent: String, expectedIsExternal: Boolean = false, expectedOpensInNewTab: Boolean = false, exactUrl: Boolean = true) {
    val link = doc.getElementById(linkId)
    assert(link.text() == expectedText, s"\n\n Link $linkId does not have text $expectedText")
    if(exactUrl == true){
      assert(link.attr("href") == expectedUrl, s"\n\n Link $linkId does not expectedUrl $expectedUrl")
    } else {
      assert(link.attr("href").contains(expectedUrl), s"\n\n Link $linkId does not contain expectedUrl $expectedUrl")
    }
    assert(link.attr("rel").contains("external") == expectedIsExternal, s"\n\n Link $linkId does not meet expectedIsExternal $expectedIsExternal")
    assert(link.attr("data-journey-click") == expectedGAEvent, s"\n\n Link $linkId does not have expectedGAEvent $expectedGAEvent")
    assert(link.attr("target").contains("_blank") == expectedOpensInNewTab, s"\n\n Link $linkId does not meet expectedOpensInNewTab $expectedGAEvent")
  }

  def assertLinkByLinkText(doc: Document, linkText: String, expectedUrl: String): Unit = {
    val links : Elements = doc.select("a")
    val link: Option[Element] = links.find(_.text() == linkText)
    assert(link.isDefined, s"Link with text '$linkText' not found")
    assert(link.get.attr("href") == expectedUrl, s"Link with text '$linkText' does not point to '$expectedUrl'")
  }

}
