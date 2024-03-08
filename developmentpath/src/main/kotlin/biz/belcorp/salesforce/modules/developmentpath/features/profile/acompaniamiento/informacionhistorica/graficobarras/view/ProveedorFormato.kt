package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import java.util.*

class ProveedorFormato {

    companion object {
        const val FORMATO_COMAS_DECIMAL = "%,.2f"
        const val FORMATO_COMAS = "%,.0f"
    }

    fun obtenerFormato(tipoGrafico: Int, numero: Float): String {
        val format = when (tipoGrafico) {
            TipoGrafico.PEDIDOS.codigo, TipoGrafico.RETENCION_6D6.codigo -> FORMATO_COMAS
            else -> FORMATO_COMAS_DECIMAL
        }

        return String.format(Locale.US, format, numero)
    }
}
