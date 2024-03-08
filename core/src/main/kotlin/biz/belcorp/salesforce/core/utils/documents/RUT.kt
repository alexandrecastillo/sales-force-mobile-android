package biz.belcorp.salesforce.core.utils.documents

import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import java.util.regex.Pattern
import kotlin.math.floor

object RUT {

    private const val PATTERN = "^[0-9]+-[0-9kK]$"

    fun validate(rut: String): Boolean {
        val pattern = Pattern.compile(PATTERN)
        val matcher = pattern.matcher(rut)
        if (!matcher.matches()) return false
        val stringRut = rut.split(HYPHEN.toRegex()).toTypedArray()
        return stringRut[1].equals(
            dv(
                stringRut[0]
            ), ignoreCase = true
        )
    }

    fun format(rut: String): String {
        with(rut.trim()) {
            val dv = this.takeLast(NUMBER_ONE)
            val document = this.substring(NUMBER_ZERO, this.length - NUMBER_ONE)
            return "$document$HYPHEN$dv"
        }
    }

    private fun dv(rut: String): String? {
        var m = 0
        var s = 1
        var t = rut.toInt()
        while (t != 0) {
            s = (s + t % 10 * (9 - m++ % 6)) % 11
            t = floor(t / 10.toDouble()).toInt()
        }
        return if (s > 0) (s - 1).toString() else "k"
    }
}
