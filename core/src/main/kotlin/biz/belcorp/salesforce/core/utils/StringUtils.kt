package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.BLANK_SPACE
import biz.belcorp.salesforce.core.constants.Constant.DOT
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_FOUR
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.PERCENTAGE
import biz.belcorp.salesforce.core.constants.Constant.PIPE
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import java.util.*

val whitespaces = "\\s+".toRegex()

private const val YOUTUBE_VIDEO_ID_PATTERN =
    "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"

fun String.replaceSpecialCharacters() = replace("á", "a")
    .replace("é", "e")
    .replace("í", "i")
    .replace("ó", "o")
    .replace("ú", "u")
    .replace("Á", "A")
    .replace("É", "E")
    .replace("Í", "I")
    .replace("Ó", "O")
    .replace("Ú", "U")

fun String.removeExtraWhiteSpaces() = replace(whitespaces, BLANK_SPACE)

fun String.separarNombre(): Triple<String, String, String> {
    val palabras = this.split(BLANK_SPACE)
    val primero = palabras.getOrNull(0) ?: EMPTY_STRING
    val segundo = palabras.getOrNull(1) ?: EMPTY_STRING
    val tercero = palabras.getOrNull(2) ?: EMPTY_STRING
    return Triple(primero, segundo, tercero)
}

fun String?.aGuionSiEsNullOVacio(): String {
    return if (isNullOrEmpty()) HYPHEN else this!!
}

fun String?.guionSiNull(): String {
    return this ?: HYPHEN
}

fun String?.guionSiVacioONull(): String {
    return if (this.isNullOrBlank()) HYPHEN else this
}

fun String?.orZero(): String = this ?: NUMBER_ZERO.toString()

fun String?.isPhoneNumberValid(): Boolean {
    val value = this ?: EMPTY_STRING
    return value.length >= 9
}

fun String.deleteHyphen() = this.replace(HYPHEN, EMPTY_STRING)

fun String.deletePipe() = this.replace(PIPE, EMPTY_STRING)

fun String.deleteDot() = this.replace(DOT, EMPTY_STRING)

fun String.deleteSpace() = this.replace(BLANK_SPACE, EMPTY_STRING)

fun String.suprimirAPartirDeGuion() = this.substringBefore(HYPHEN).trim()

fun String.suprimirAntesAGuion() = substringAfter(HYPHEN)

fun String.toUpperCase(value: Boolean = true) =
    if (value) this.toUpperCase(Locale.getDefault()) else this

fun String.primeraPalabra(): String {
    val palabras = this.trim().split(BLANK_SPACE)
    return palabras.getOrNull(NUMBER_ZERO) ?: EMPTY_STRING
}

fun String?.obtenerGuionONumeroConPrefijo(prefijo: String?): String? {
    return if (isNullEmptyOrDashes()) {
        HYPHEN
    } else {
        this?.takeIf { it.startsWith(Constant.PLUS) } ?: "${Constant.PLUS}$prefijo $this"
    }
}

private fun String?.isNullEmptyOrDashes() =
    this.isNullOrEmpty() || this.all { it.toString() == HYPHEN }

fun String.takeLastTwo() = this.takeLast(Constant.NUMERO_DOS)

fun String?.emptyIfNull() = this ?: EMPTY_STRING

fun String?.isCodeEmptyOrNull() = this.orEmpty().deleteHyphen().isEmpty()

fun String.isEmptyOrDash() = this.isEmpty() || this == HYPHEN

fun String.isNotEmptyNeitherDash() = !this.isEmptyOrDash()

fun String.removeFirstWord(): String {
    val elements = this.split(BLANK_SPACE)
    if (elements.size < NUMBER_TWO) return this
    return elements.excludeFirst().joinToString(BLANK_SPACE)
}

fun String.capitalizeAll(): String {
    return split(BLANK_SPACE).joinToString(BLANK_SPACE) {
        it.toLowerCase(Locale.getDefault()).capitalize()
    }.trim()
}

/**
 * This is a basic extension to divide a name in tokens
 * not composed names are allowed in this first version,
 * if names are incomplete this decides based on the basic
 * structure of a name
 */
fun String.toQuadruple(): Quadruple<String, String, String, String> {
    val words = split(BLANK_SPACE).filter { it.trim().isNotEmpty() }
    return when (words.size) {
        NUMBER_ZERO -> Quadruple(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        NUMBER_ONE -> Quadruple(words[NUMBER_ZERO], EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        NUMBER_TWO -> Quadruple(words[NUMBER_ZERO], EMPTY_STRING, words[NUMBER_ONE], EMPTY_STRING)
        NUMBER_THREE -> Quadruple(
            words[NUMBER_ZERO],
            EMPTY_STRING,
            words[NUMBER_ONE],
            words[NUMBER_TWO]
        )
        NUMBER_FOUR -> Quadruple(
            words[NUMBER_ZERO],
            words[NUMBER_ONE],
            words[NUMBER_TWO],
            words[NUMBER_THREE]
        )
        else -> Quadruple(
            words[NUMBER_ZERO],
            EMPTY_STRING,
            words[words.size - NUMBER_TWO],
            words[words.size - NUMBER_ONE]
        )
    }
}

fun String.appendIf(
    condition: Boolean,
    value: String,
    lineBreak: Boolean = false
): String {
    return if (condition) this + if (lineBreak) "\n $value" else " $value" else this
}

fun String?.ifEmptyOrNullToZero(): String = if (this.isNullOrEmpty()) NUMBER_ZERO.toString() else this

fun String.toDoubleOrZero() = this.toDoubleOrNull() ?: ZERO_DECIMAL

fun String.concatPercentageSymbol() = this.plus(PERCENTAGE)
