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

package models

import scala.util.Random

class SaUtrGenerator(random: Random = new Random) extends Modulus11Check {
  def this(seed: Int) = this(new scala.util.Random(seed))

  def nextSaUtr: SaUtr = {
    val suffix = f"${random.nextInt(100000)}%09d"
    val checkCharacter  = calculateCheckCharacter(suffix)
    SaUtr(s"$checkCharacter$suffix")
  }
}

trait ModulusCheck {

  protected val checkString: String
  protected val mod: Int
  protected val weights: List[Int]

  protected def isCheckCorrect(utr: String, checkPosition: Int): Boolean =
    utr.charAt(checkPosition) == getCheckCharacter(utr, checkPosition + 1)

  protected def calculateCheckCharacter(utr: String) = getCheckCharacter(utr, 0)

  private def getCheckCharacter(utr: String, offset: Int): Char = {
    val sum = weights.zipWithIndex.collect {
      case (weight, index) if index + offset < utr.length =>
        val char = utr.charAt(index + offset)
        if (char.isLetter) {
          weight * (char.asDigit + mod)
        } else {
          weight * char.asDigit
        }
    }.sum

    checkString.charAt(sum % mod)
  }

}

trait Modulus11Check extends ModulusCheck {

  override protected val checkString = "21987654321"
  override protected val mod = 11
  override protected val weights = List(6, 7, 8, 9, 10, 5, 4, 3, 2)

}

trait Modulus23Check extends ModulusCheck {

  override protected val checkString = "ABCDEFGHXJKLMNYPQRSTZVW"
  override protected val mod = 23
  override protected val weights = List(9, 10, 11, 12, 13, 8, 7, 6, 5, 4, 3, 2, 1)

}
