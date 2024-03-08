package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.view

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico

class ProveedorColorBarras {

    fun obtenerColor(tipoGrafico: Int): Int {
        return when(tipoGrafico) {
            TipoGrafico.PROMEDIO.codigo -> R.color.grafico_historica_region_promedio
            TipoGrafico.RETENCION_6D6.codigo -> R.color.grafico_historica_region_retencion6d6
            TipoGrafico.RETENCION_ACTIVAS.codigo -> R.color.grafico_historica_region_retencionactivas
            TipoGrafico.PRODUCTIVAS.codigo -> R.color.grafico_historica_region_productivas
            TipoGrafico.CAMBIO_NIVEL.codigo -> R.color.grafico_historica_region_cambionivel
            TipoGrafico.VENTAS.codigo -> R.color.grafico_historica_region_venta
            TipoGrafico.PEDIDOS.codigo -> R.color.grafico_historica_region_pedidos
            else -> R.color.informacion_historica_barras
        }
    }
}
