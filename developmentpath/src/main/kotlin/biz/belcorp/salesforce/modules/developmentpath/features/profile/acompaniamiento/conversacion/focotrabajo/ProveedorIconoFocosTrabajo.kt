package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo

import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico

class ProveedorIconoFocosTrabajo {

    fun obtenerIconoPorTipoGrafico(tipoGrafico: Int): Int {
        return when (tipoGrafico) {
            TipoGrafico.PROMEDIO.codigo -> R.drawable.ic_indicador_region_promedio_white
            TipoGrafico.RETENCION_6D6.codigo -> R.drawable.ic_indicador_region_retencion_white
            TipoGrafico.RETENCION_ACTIVAS.codigo -> R.drawable.ic_indicador_region_retencionactivas_white
            TipoGrafico.PRODUCTIVAS.codigo -> R.drawable.ic_indicador_region_productivas_white
            TipoGrafico.CAMBIO_NIVEL.codigo -> R.drawable.ic_indicador_region_cambionivel_white
            TipoGrafico.VENTAS.codigo -> R.drawable.ic_indicador_region_ventas_white
            TipoGrafico.PEDIDOS.codigo -> R.drawable.ic_indicador_region_pedidos_white
            else -> R.drawable.ic_default
        }
    }
}
