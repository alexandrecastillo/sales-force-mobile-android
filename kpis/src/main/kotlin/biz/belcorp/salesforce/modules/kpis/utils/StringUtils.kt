package biz.belcorp.salesforce.modules.kpis.utils

import biz.belcorp.salesforce.core.constants.Constant

object StringUtils {

    const val ONE_DOUBLE_CONSTANT = 1.0
    const val ZERO_DOUBLE_CONSTANT = 1.0

    @JvmStatic
    fun getSegmentValues(s: String): List<String> {
        return s.split(Constant.DELIMITER).toTypedArray().toList()
    }

    @JvmStatic
    fun capitalize(s: String?): String {
        if (s == null)
            return Constant.EMPTY_STRING
        return if (s.isEmpty()) s.toUpperCase() else s.substring(
            0,
            1
        ).toUpperCase() + s.substring(1).toLowerCase()

    }
    @JvmStatic
    fun capitalizePhrase(phrase: String?): String {

        if (phrase == null)
            return Constant.EMPTY_STRING

        val splited =
            phrase.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

        val stringBuilder = StringBuilder()
        stringBuilder.append(Constant.EMPTY_STRING)
        for (word in splited) {
            stringBuilder.append(capitalize(word))
            stringBuilder.append(Constant.BLANK_SPACE)
        }

        return stringBuilder.toString().trim { it <= ' ' }
    }

    fun checkStringToOperateWithNumbersDouble(s: String?) = if(s.isNullOrEmpty()) ZERO_DOUBLE_CONSTANT else s.toDouble()

    fun checkStringToOperateWithNumbersDenominator(s: String?) = if(s.isNullOrEmpty()) ONE_DOUBLE_CONSTANT else s.toDouble()

    fun checkNumberDivisionByZero(i: Int) = if(i == 0) ONE_DOUBLE_CONSTANT else i.toDouble()

}
