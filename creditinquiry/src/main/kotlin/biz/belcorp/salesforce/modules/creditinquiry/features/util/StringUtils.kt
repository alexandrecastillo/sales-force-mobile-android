package biz.belcorp.salesforce.modules.creditinquiry.features.util

import biz.belcorp.salesforce.core.constants.Constant
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object StringUtils {

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

    @JvmStatic
    fun capitalizePhraseTrim(phrase: String?): String {

        if (phrase == null)
            return Constant.EMPTY_STRING

        val splited =
            phrase.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

        val stringBuilder = StringBuilder()
        stringBuilder.append(Constant.EMPTY_STRING)
        for (word in splited) {
            stringBuilder.append(capitalize(word).trim { it <= ' ' })
            stringBuilder.append(Constant.BLANK_SPACE)
        }

        return stringBuilder.toString().trim { it <= ' ' }
    }

    @JvmStatic
    fun stringOrNull(str: Any?): String {
        return str?.toString() ?: ""
    }

    @JvmStatic
    fun stringOrNull(str: Any?, defaulValue: String): String {
        return str?.toString() ?: defaulValue
    }

    @JvmStatic
    @Throws(UnsupportedEncodingException::class)
    fun stringEncodeUTF8(cadena: String?): String {
        var cadenaCod = ""

        if (cadena == null) {
            return cadenaCod
        } else {
            cadenaCod = URLEncoder.encode(cadena, "utf-8")
        }
        return cadenaCod

    }

}
