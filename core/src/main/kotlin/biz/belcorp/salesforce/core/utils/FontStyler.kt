package biz.belcorp.salesforce.core.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat

object FontStyler {

    fun establecerTexto(texto: String): Builder {
        return Builder(texto)
    }

    class Builder(
        private val texto: String,
        private var delimitador: String? = null,
        private var fuentePrimaria: Typeface? = null,
        private var fuenteSecundaria: Typeface? = null,
        private var colorPrimario: Int? = null,
        private var colorSecundario: Int? = null,
        private var context: Context? = null
    ) {

        private val delimitadorValidado
            get() = requireNotNull(delimitador) {
                "Se debe configurar el delimitador"
            }

        private val fuentePrimariaValidada
            get() = requireNotNull(fuentePrimaria) {
                "Se debe configurar la fuente primaria"
            }

        private val fuenteSecundariaValidada
            get() = requireNotNull(fuenteSecundaria) {
                "Se debe configurar la fuente secundaria"
            }

        private val colorPrimarioValidado
            get() = requireNotNull(colorPrimario) {
                "Se debe configurar el color primario"
            }

        private val contextValidado
            get() = requireNotNull(context) {
                "Se debe configurar el context"
            }

        fun conContext(context: Context): Builder {
            this.context = context

            return this
        }

        fun conDelimitador(delimitador: String): Builder {
            this.delimitador = delimitador

            return this
        }

        fun conFuentePrimaria(fuentePrimaria: TipoFuente): Builder {
            this.fuentePrimaria = obtenerTypeFace(fuentePrimaria)

            return this
        }

        fun conFuenteSecundaria(fuenteSecundaria: TipoFuente): Builder {
            this.fuenteSecundaria = obtenerTypeFace(fuenteSecundaria)

            return this
        }

        private fun obtenerTypeFace(tipoFuente: TipoFuente): Typeface? {
            return contextValidado.getTypeFace(tipoFuente.font)
        }

        fun conColorPrimarioDesdeRecurso(recursoId: Int): Builder {
            this.colorPrimario = obtenerColor(recursoId)

            return this
        }

        fun conColorSecundarioDesdeRecurso(recursoId: Int): Builder {
            this.colorSecundario = obtenerColor(recursoId)

            return this
        }

        private fun obtenerColor(recursoId: Int): Int {
            return ContextCompat.getColor(contextValidado, recursoId)
        }

        fun procesar(): SpannableStringBuilder {
            return Procesador(
                texto = texto,
                delimitador = delimitadorValidado,
                fuentePrimaria = fuentePrimariaValidada,
                fuenteSecundaria = fuenteSecundariaValidada,
                colorPrimario = colorPrimarioValidado,
                colorSecundario = colorSecundario ?: colorPrimarioValidado
            ).procesar()
        }
    }

    class Procesador(
        val texto: String,
        val delimitador: String,
        val fuentePrimaria: Typeface,
        val fuenteSecundaria: Typeface,
        val colorPrimario: Int,
        val colorSecundario: Int
    ) {

        fun procesar(): SpannableStringBuilder {
            val textoDividido = agregarDelimitadorAlInicioYFin().split(delimitador)
            val textoProcesado = SpannableStringBuilder("")

            textoDividido.forEachIndexed { indice, division ->
                textoProcesado.append(obtenerSpannablePorIndice(indice, division))
            }

            return textoProcesado
        }

        private fun agregarDelimitadorAlInicioYFin() = delimitador + texto + delimitador

        private fun obtenerSpannablePorIndice(
            indice: Int,
            textoDividido: String
        ): SpannableStringBuilder {
            return SpannableStringBuilder(textoDividido).apply {
                setSpan(
                    obtenerTypefaceSpanPorIndice(indice),
                    0,
                    textoDividido.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        private fun obtenerTypefaceSpanPorIndice(indice: Int): CustomTypefaceSpan {
            return if (indice % 2 == 0) {
                CustomTypefaceSpan("default", fuentePrimaria, colorPrimario)
            } else {
                CustomTypefaceSpan("default", fuenteSecundaria, colorSecundario)
            }
        }

    }
}
