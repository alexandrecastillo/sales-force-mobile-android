package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import biz.belcorp.salesforce.core.utils.changeDateFormat
import java.util.regex.Pattern

class Validation {

    companion object {

        const val LETTER = 1
        const val NUMBER = 2
        const val DECIMAL = 3
        const val ALPHANUMERIC = 4
        const val EMAIL = 5
        const val DATE = 6
        const val URL = 7
        const val MONEY = 8
        const val EMPTY = 9
        const val MIN_LENGTH = 10
        const val LISTA = 11
        const val CHECKED = 12
        const val REPEAT_NUMBER = 13
        const val REPEAT_CHARACTER = 14
        const val INICIO = 15
        const val CALLABLE = 16
        const val NO_SELECTION = 17
        const val LIMIT_LENGTH_7_10 = 18
        const val NOT_INICIO = 19
        const val DATE_FORMAT = 20
        const val NOTJUST0 = 21
        const val STARTIN2 = 22
        const val HAS_SPACES = 23

        fun validate(type: Int, str: String, value: Any?): Boolean {
            return when {
                EMPTY == type -> !isEmpty(str)
                MIN_LENGTH == type -> !isMinLength(str, value as Int)
                LISTA == type -> isSelected(Integer.parseInt(str), value as Int)
                CHECKED == type -> isChecked(Integer.parseInt(str), value as Int)
                REPEAT_NUMBER == type -> !isRepeat(str, value as Int)
                REPEAT_CHARACTER == type -> !isRepeatCharacter(str, value.toString())
                INICIO == type -> isInicio(str, value.toString())
                NOT_INICIO == type -> !isInicio(str, value.toString())
                CALLABLE == type -> isCallable(value as ValidCommand)
                NO_SELECTION == type -> !isNoSelection(str)
                LIMIT_LENGTH_7_10 == type -> isLimit7or1OLength(str)
                NOTJUST0 == type -> notContainJust0(str)
                DATE_FORMAT == type -> validateDateFormat(str, value as String)
                STARTIN2 == type -> isStartin2(str)
                ALPHANUMERIC == type -> isAlphaNumeric(str)
                HAS_SPACES == type -> hasSpaces(str)
                else -> str.matches(getPattern(type).toRegex())
            }
        }

        private fun validateDateFormat(strDate: String, dateFormats: String): Boolean {
            val formats = separateFormat(dateFormats)

            return !strDate.changeDateFormat(formats.first, formats.second).isNullOrBlank()
        }

        private fun separateFormat(value: String): Pair<String, String> {
            val myStrSplitedVal = value.split("|")
            return Pair(
                    myStrSplitedVal[0],
                    myStrSplitedVal[1]
            )

        }

        private fun isNoSelection(str: String): Boolean {
            return str == "-1" || str.isEmpty()
        }

        private fun getPattern(type: Int): String {
            return when (type) {
                LETTER -> "^[a-zA-ZáéíóúAÉÍÓÚÑñ\\s]+$"
                NUMBER -> "^[\\d\\s]+$"
                DECIMAL -> "^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$"
                EMAIL -> "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$"
                DATE -> "^((([0][1-9]|[12][\\\\d])|[3][01])[-\\\\/]([0][13578]|[1][02])[-\\\\/][1-9]\\\\d\\\\d\\\\d)|((([0][1-9]|[12][\\\\d])|[3][0])[-\\\\/]([0][13456789]|[1][012])[-\\\\/][1-9]\\\\d\\\\d\\\\d)|(([0][1-9]|[12][\\\\d])[-\\\\/][0][2][-\\\\/][1-9]\\\\d([02468][048]|[13579][26]))|(([0][1-9]|[12][0-8])[-\\\\/][0][2][-\\\\/][1-9]\\\\d\\\\d\\\\d)$"
                URL -> "^(https?|s?ftp):\\/\\/(((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(#((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$"
                MONEY -> "^\\d+(?:\\.\\d{1,2})?$"
                ALPHANUMERIC -> "^[a-zA-Z0-9]+\$"
                else -> "^[\\w\\dáéíóúAÉÍÓÚÑñ\\s]+$"
            }
        }

        private fun isEmpty(str: String): Boolean {
            return str.trim { it <= ' ' }.isEmpty()
        }

        private fun isMinLength(str: String, minLength: Int): Boolean {
            return str.trim { it <= ' ' }.length < minLength
        }

        private fun isLimit7or1OLength(str: String): Boolean {
            return str.trim { it <= ' ' }.length == 7 || str.trim { it <= ' ' }.length == 1  || str.trim { it <= ' ' }.length == 10
        }

        private fun notContainJust0(str: String): Boolean {
            var just0 = false
            for (i in str.indices) {
                just0 = str[i].toString() != 0.toString()
                if (just0) break
            }
            return just0
        }

        private fun isSelected(pos: Int, def: Int): Boolean {
            return pos > def
        }

        private fun isChecked(checked: Int, def: Int): Boolean {
            return checked > def
        }

        private fun isRepeat(str: String, max: Int): Boolean {
            val reg = "(\\d)\\1{$max}+"
            return Pattern.compile(reg).matcher(str).find()
        }

        private fun isRepeatCharacter(str: String, c: String): Boolean {
            val reg = "^$c|([$c])\\1{1}|$c$"
            return Pattern.compile(reg).matcher(str).find()
        }

        private fun isInicio(str: String, value: String): Boolean {
            return str.indexOf(value) == 0
        }

        private fun isCallable(callable: ValidCommand): Boolean {
            return callable.execute()
        }

        private fun isStartin2(str: String): Boolean {
            val reg = "^[0-9]{8}\$|^(1|2)[0-9]{9}\$"
            return Pattern.compile(reg).matcher(str).matches()
        }

        private fun isAlphaNumeric(str: String): Boolean {
            return Pattern.compile(getPattern(ALPHANUMERIC)).matcher(str).matches()
        }

        private fun hasSpaces(str: String) : Boolean{
            for (i in str.indices) {
                if(str[i].toString() == " ") return false
            }
            return true
        }

        interface ValidCommand {
            fun execute(): Boolean
        }
    }
}
