package biz.belcorp.salesforce.modules.developmentpath.utils

import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import biz.belcorp.salesforce.components.utils.CustomTypefaceSpan
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Constants
import java.util.regex.Pattern

fun String.toUri(): Uri = Uri.parse(this)

fun String.iniciales(): String {
    var iniciales = Constant.EMPTY_STRING
    if (this.trim().isNotEmpty()) {
        val palabras = this.trim().split("\\s+".toRegex())
        if (palabras.size > Constant.NUMERO_UNO) {
            iniciales = "${palabras[0][0].toUpperCase()}${palabras[1][0].toUpperCase()}"
        }
    }
    return iniciales
}

fun String.recuperarNombreCorto(): String {
    var nombreCorto = Constant.EMPTY_STRING
    if (this.trim().isNotEmpty()) {
        val palabras = this.trim().split("\\s+".toRegex())
        nombreCorto = when (palabras.size) {
            Constant.NUMERO_CERO -> Constant.EMPTY_STRING
            Constant.NUMERO_UNO -> WordUtils.capitalizeFully(palabras[0])
            else -> WordUtils.capitalizeFully("${palabras[0]} ${palabras[1]}")
        }
    }
    return nombreCorto
}

fun String.conPrimeraLetraMayus(): String {
    var cadena = this
    if (this.length > Constant.NUMERO_UNO) {
        cadena = this[0].toUpperCase() + cadena.substring(1)
    }
    return cadena
}

fun String?.obtenerPrimerLetra(): String {
    return if (this.isNullOrBlank())
        ""
    else
        this.trim().substring(0, 1).toUpperCase()
}

fun String.eliminarLine(): String {
    return this.replace(Constant.HYPHEN, Constant.EMPTY_STRING)
}

fun String?.agregarDigitoVerificador(digito: String?) =
    if (digito.isNullOrBlank())
        this ?: Constant.EMPTY_STRING
    else
        "$this - $digito"

fun String.quitarComas() = this.replace(Constant.COMMA.toString(), Constant.EMPTY_STRING, true)

fun String.reemplazarSaltoLinea() = this.replace("\\n", "\n")

fun String.establecerDistintasFuentes(
    caracterSeparador: String,
    fontPrincipal: Typeface?,
    fontSecundario: Typeface?,
    colorPrincipal: Int,
    colorSecundario: Int
): SpannableStringBuilder? {

    val textoDividido = split(caracterSeparador)

    when {
        textoDividido.size > Constant.NUMERO_UNO -> {
            val textoPrimario = textoDividido[0]
            val textoSecundario = textoDividido[1]

            val spannablePrimario = SpannableStringBuilder(textoPrimario)
            val spannableSecundario = SpannableStringBuilder(textoSecundario)

            spannablePrimario.setSpan(
                CustomTypefaceSpan(fontPrincipal ?: Typeface.DEFAULT, colorPrincipal),
                Constant.NUMERO_CERO,
                textoPrimario.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableSecundario.setSpan(
                CustomTypefaceSpan(fontSecundario ?: Typeface.DEFAULT, colorSecundario),
                Constant.NUMERO_CERO,
                textoSecundario.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannablePrimario.append(spannableSecundario)
        }
        textoDividido.size == Constant.NUMERO_UNO -> {
            val textoPrimario = textoDividido[0]
            val spannablePrimario = SpannableStringBuilder(textoPrimario)
            spannablePrimario.setSpan(
                CustomTypefaceSpan(fontPrincipal ?: Typeface.DEFAULT, colorPrincipal),
                Constant.NUMERO_CERO,
                textoPrimario.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            return spannablePrimario
        }
        else -> return null
    }
}

fun String?.guionSiEsNullOVacio(): String {
    return if (isNullOrBlank()) Constant.HYPHEN else this!!
}

fun String.reemplazarSaltoLineaPorEspacio() = this.replace("\\n", Constant.BLANK_SPACE)

fun String.reemplazarSaltoLineaPorEspacioAlrededor() = this.replace("\\n", " \n ")

private const val YOUTUBE_VIDEO_ID_PATTERN = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"

fun String?.getIdYoutube(): String {
    val compiledPattern = Pattern.compile(YOUTUBE_VIDEO_ID_PATTERN)
    val matcher = compiledPattern.matcher(this)
    return if (matcher.find()) {
        matcher.group(1)
    } else {
        Constants.EMPTY_STRING
    }
}
