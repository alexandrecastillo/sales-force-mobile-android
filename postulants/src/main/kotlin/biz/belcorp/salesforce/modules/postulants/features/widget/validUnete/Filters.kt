package biz.belcorp.salesforce.modules.postulants.features.widget.validUnete

import android.text.InputFilter
import java.util.*


class Filters {

    companion object {

        fun filterMaxLength(maxLength: Int): InputFilter {
            return InputFilter.LengthFilter(maxLength)
        }

        fun filterNumber(): InputFilter {
            return InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (!Character.isDigit(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }

        fun filterNumber(max: Int): Array<InputFilter> {
            return if (max < 1)
                arrayOf(filterNumber())
            else
                arrayOf(filterNumber(), filterMaxLength(max))
        }

        fun filterAlphanumeric(): InputFilter {
            return InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (!Character.isLetterOrDigit(source[i]) && !Character.isSpaceChar(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }

        fun filterAlphanumeric(max: Int): Array<InputFilter> {
            return if (max < 1)
                arrayOf(filterAlphanumeric())
            else
                arrayOf(filterAlphanumeric(), filterMaxLength(max))
        }

        fun filterAlphanumericHyphen(): InputFilter {
            val chars = Arrays.asList('-')
            return InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (verifyCharacters(source, i, chars)) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }

        fun filterAlphanumericHyphen(max: Int): Array<InputFilter> {
            return if (max < 1)
                arrayOf(filterAlphanumericHyphen())
            else
                arrayOf(filterAlphanumericHyphen(), filterMaxLength(max))
        }

        fun filterLetters(): InputFilter {
            return InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (!Character.isLetter(source[i]) && !Character.isSpaceChar(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }

        fun filterLetters(max: Int): Array<InputFilter> {
            return if (max < 1)
                arrayOf(filterLetters())
            else
                arrayOf(filterLetters(), filterMaxLength(max))
        }

        fun filterDireccion(): InputFilter {

            val chars = Arrays.asList('-', '#', '/', '.', ',')

            return InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {

                    if (verifyCharacters(source, i, chars)) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }

        private fun verifyCharacters(source: CharSequence, i: Int, chars: MutableList<Char>) =
            !Character.isLetterOrDigit(source[i]) &&
                chars.indexOf(source[i]) < 0 &&
                !Character.isSpaceChar(source[i])


    }
}
