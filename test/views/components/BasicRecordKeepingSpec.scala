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

package views.components

import config.FrontendAppConfig
import org.jsoup.Jsoup
import views.behaviours.ViewBehaviours
import views.html.components.basic_record_keeping

class BasicRecordKeepingSpec extends ViewBehaviours {

  val view = basic_record_keeping(appConfig = frontendAppConfig)(messages)

  "Self Assessment Expenses view" must {

    "contain correct content" in {

      val doc = Jsoup.parse(view.toString)

      doc.text() must include("You must record any money going in and out of your business, whether the transaction is made:")

      doc.text() must include("in person")

      doc.text() must include("by phone")

      doc.text() must include("online")

      doc.text() must include("Keep your records on paper or digitally using a computer. These include items such as invoices, sale receipts," +
        " proof of purchases and bank statements. Remember your records must be accurate, clear and complete.")

      doc.text() must include("Some software suppliers offer free record keeping apps for small businesses. How you keep " +
        "your records will depend on whether you use the traditional or cash basis method of accounting, and how you work out your expenses.")

      doc.text() must include("You’ll find it easier if you organise your income" +
        " and expenses into categories and update these regularly. Remember to back up your files if you’re keeping your records digitally.")

      doc.text() must include("You must keep your business and " +
        "personal transactions separate. It is not an allowable business expense to use money from your business for personal purposes.")

      doc.text() must include("If you’re VAT registered, you’ll also need to keep " +
        "records of how much VAT you’ve paid your suppliers and charged your customers.")

      doc.text() must include("If your income is above the VAT threshold, you’ll need to submit your records to us using Making Tax Digital software.")

      doc.text() must include("You must keep your records for at least 5 years after the 31 January submission deadline " +
        "of the relevant tax year. HMRC may check your records to make sure you’re paying the right amount of tax.")

      doc.text() must include("Example: For the tax year 2019 to 2020, the submission" +
        " deadline is 31 January 2021. Keep your records until at least 31 January 2026.")

    }

    "have correct links" in {
      val doc = Jsoup.parse(view.toString)
      println("0")
      assertLinkById(
        doc,
        "record-keeping-video",
        "Video - What self-employment records do I need to keep? (opens in new tab)",
        "https://www.youtube.com/watch?v=VDECQMrnZ-o",
        expectedOpensInNewTab = true
      )

      println("1")
      assertLinkById(
        doc,
        "business-records",
        "Business records if you’re self-employed (opens in new tab)",
        "https://www.gov.uk/self-employed-records",
        expectedOpensInNewTab = true
      )
      println("1")
      assertLinkById(
        doc,
        "keeping-pay-tax",
        "Keeping your pay and tax records (opens in new tab)",
        "https://www.gov.uk/self-employed-records/what-records-to-keep",
        expectedOpensInNewTab = true
      )


    }

  }

}
