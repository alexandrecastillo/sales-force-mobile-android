package biz.belcorp.salesforce.modules.developmentpath.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.components.utils.CustomTypefaceSpan
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.core.utils.getTypeFace

object MultiFontStyler {

    fun establecerTexto(texto: String): Builder {
        return Builder(texto)
    }

    class Builder(
        private val texto: String,
        private var delimitador: String? = null,
        private var fuentePrimaria: Typeface? = null,
        private var fuenteSencundaria: Typeface? = null,
        private var colorPrincipal: Int? = null,
        private var coloresSecundarios: List<Int> = emptyList(),
        private var context: Context? = null
    ) {

        private val delimitadorValido
            get() = requireNotNull(delimitador) { "Indicar delimitador para el texto" }

        private val fuentePrincipalValida
            get() = requireNotNull(fuentePrimaria) { "Indicar fuente principal para el texto" }

        private val fuenteSecundariaValida
            get() = requireNotNull(fuenteSencundaria) { "Indicar fuente principal para el texto" }

        private val colorPrincipalValido
            get() = requireNotNull(colorPrincipal) { "Indicar color principal para el texto" }

        private val contextValido
            get() = requireNotNull(context) { "Indicar contexto" }

        fun establecerDelimitador(delimitador: String): Builder {
            this.delimitador = delimitador
            return this
        }

        fun establecerFuentePrimaria(tipoFuente: TipoFuente): Builder {
            this.fuentePrimaria = contextValido.getTypeFace(tipoFuente.font)
            return this
        }

        fun establecerFuenteSecundaria(tipoFuente: TipoFuente): Builder {
            this.fuenteSencundaria = contextValido.getTypeFace(tipoFuente.font)
            return this
        }

        fun establecerColorPrimario(color: Int): Builder {
            this.colorPrincipal = color
            return this
        }

        fun establecerColoresSencundarios(colores: List<Int>): Builder {
            val coloresSencundarios = arrayListOf<Int>()
            colores.forEach { color ->
                coloresSencundarios.add(obtenerColor(color))
            }
            this.coloresSecundarios = coloresSencundarios
            return this
        }

        fun establecerContexto(context: Context): Builder {
            this.context = context
            return this
        }


        private fun obtenerColor(color: Int): Int {
            return ContextCompat.getColor(contextValido, color)
        }

        fun procesar(): SpannableStringBuilder {
            return Procesador(
                texto = texto,
                delimitador = delimitadorValido,
                fuentePrincipal = fuentePrincipalValida,
                fuenteSecundaria = fuenteSecundariaValida,
                colorPrincipal = colorPrincipalValido,
                coloresSecundarios = coloresSecundarios
            ).procesar()
        }
    }

    class Procesador(
        val texto: String,
        val delimitador: String,
        val fuentePrincipal: Typeface,
        val fuenteSecundaria: Typeface,
        val colorPrincipal: Int,
        val coloresSecundarios: List<Int>
    ) {

        companion object {
            private const val ESPACIO = " "
            private const val VACIO = ""
            private const val TOTAL_DELIMITADORES = 2
            private const val DEFAULT_FAMILY = "default"
        }

        fun procesar(): SpannableStringBuilder {
            val textos = obtenerTextos(texto)
            val textoProcesado = SpannableStringBuilder(VACIO)
            textos.forEach { elemento ->
                textoProcesado.append(obtenerTextosColoreados(elemento))
            }
            return textoProcesado
        }

        private fun obtenerTextosColoreados(elemento: TextoColor): SpannableStringBuilder {
            val texto = elemento.texto.replace(delimitador, "").plus(ESPACIO)
            return SpannableStringBuilder(texto).apply {
                setSpan(
                    CustomTypefaceSpan(decidirFuente(elemento.texto), elemento.color),
                    Constant.NUMERO_CERO,
                    texto.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        private fun decidirFuente(texto: String): Typeface {
            return if (!texto.contains(delimitador)) fuentePrincipal
            else fuenteSecundaria
        }

        private fun obtenerTextos(texto: String): List<TextoColor> {
            val palabrasParaReconstruir = texto.split(ESPACIO).filter { it.trimEnd().isNotEmpty() }
            val palabras = reconstruirPalabras(palabrasParaReconstruir)
            val textos = arrayListOf<TextoColor>()
            var indiceColor = Constant.NUMERO_CERO
            for (palabra in palabras) {
                if (palabra.contains(delimitador)) {
                    textos.add(TextoColor(palabra, decidirColor(indiceColor)))
                    indiceColor++
                } else {
                    textos.add(TextoColor(palabra, colorPrincipal))
                }
            }
            return textos
        }

        private fun reconstruirPalabras(palabras: List<String>): List<String> {
            val palabrasReconstruidas = arrayListOf<String>()
            val palabrasPorReconstruir = arrayListOf<String>()
            var parEncontrado = Constant.NUMERO_CERO

            palabras.forEach {
                val palabraSinEspacios = it.trim()
                if (!contieneUnDelimitador(palabraSinEspacios) && parEncontrado == Constant.NUMERO_CERO) palabrasReconstruidas.add(
                    palabraSinEspacios
                )
                else {
                    when {
                        contieneDosDelimitadores(palabraSinEspacios) -> palabrasReconstruidas.add(
                            palabraSinEspacios
                        )
                        contieneUnDelimitador(palabraSinEspacios) -> {
                            palabrasPorReconstruir.add(palabraSinEspacios)
                            parEncontrado++
                        }
                        else -> palabrasPorReconstruir.add(palabraSinEspacios)
                    }

                    if (parEncontrado == Constant.NUMERO_DOS) {
                        parEncontrado = Constant.NUMERO_CERO
                        val palabra = reconstruirPalabraAPintar(palabrasPorReconstruir)
                        palabrasReconstruidas.add(palabra)
                        palabrasPorReconstruir.clear()
                    }
                }
            }
            palabrasReconstruidas.addAll(palabrasPorReconstruir)
            return palabrasReconstruidas
        }

        private fun contieneDosDelimitadores(palabra: String): Boolean {
            return palabra.sumBy { if (it.toString() == delimitador) Constant.NUMERO_UNO else Constant.NUMERO_CERO } == TOTAL_DELIMITADORES
        }

        private fun contieneUnDelimitador(palabra: String) = palabra.contains(delimitador)

        private fun reconstruirPalabraAPintar(palabras: List<String>) =
            palabras.joinToString(ESPACIO)

        private fun decidirColor(posicion: Int): Int {
            return if (posicion > coloresSecundarios.size - Constant.NUMERO_UNO) colorPrincipal
            else coloresSecundarios[posicion]
        }
    }

    data class TextoColor(val texto: String, val color: Int)
}
