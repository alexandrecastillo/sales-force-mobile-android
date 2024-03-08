package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.textColor
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import java.util.*
import biz.belcorp.salesforce.base.R as BaseR

class EtiquetaTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var modelo: Etiqueta = Etiqueta.inicializarEnBlanco()
        set(value) {
            field = value
            redibujar()
        }

    private fun redibujar() {
        text = modelo.obtenerStringDeEtiqueta()
        textColor = modelo.obtenerColorDeEtiqueta()
        calcularVisibilidad()
    }

    fun actualizarTexto(valor: String) {
        modelo.valor = valor
        redibujar()
    }

    fun actualizarTexto(recursoId: Int) {
        modelo.idRecurso = recursoId
        redibujar()
    }

    private fun Etiqueta.obtenerColorDeEtiqueta(): Int {
        val recurso = when (tipo) {
            Etiqueta.Tipo.LIGERO -> BaseR.color.gray_4
            Etiqueta.Tipo.FUERTE -> BaseR.color.black
            Etiqueta.Tipo.NEGATIVO -> BaseR.color.negativo
        }

        return getColor(recurso)
    }

    private fun Etiqueta.obtenerStringDeEtiqueta(): String {
        return when {
            recursoVacioValido -> context.getString(idRecursoVacio)
            recursoValido -> context.getString(idRecurso).toUpperCase(Locale.getDefault())
            else -> valor
        }
    }

    private fun calcularVisibilidad() {
        visible(modelo.seMuestraAlgo)
    }
}
