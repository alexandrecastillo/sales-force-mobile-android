package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_CAMBIO_NIVEL
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_PEDIDOS
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_PRODUCTIVAS
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_PROMEDIO
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_RETENCION_6D6
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_RETENCION_ACTIVAS
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico.Builder.Companion.COD_VENTAS

enum class TipoGrafico(val codigo: Int) {

    PROMEDIO(codigo = COD_PROMEDIO),
    RETENCION_6D6(codigo = COD_RETENCION_6D6),
    RETENCION_ACTIVAS(codigo = COD_RETENCION_ACTIVAS),
    PRODUCTIVAS(codigo = COD_PRODUCTIVAS),
    CAMBIO_NIVEL(codigo = COD_CAMBIO_NIVEL),
    VENTAS(codigo = COD_VENTAS),
    PEDIDOS(codigo = COD_PEDIDOS),
    NINGUNO(codigo = -1);

    class Builder {
        companion object {

            const val COD_PROMEDIO = 0
            const val COD_RETENCION_6D6 = 1
            const val COD_RETENCION_ACTIVAS = 2
            const val COD_PRODUCTIVAS = 3
            const val COD_CAMBIO_NIVEL = 4
            const val COD_VENTAS = 5
            const val COD_PEDIDOS = 6

            fun construir(codigo: Int): TipoGrafico {
                return when (codigo) {
                    COD_PROMEDIO -> PROMEDIO
                    COD_RETENCION_6D6 -> RETENCION_6D6
                    COD_RETENCION_ACTIVAS -> RETENCION_ACTIVAS
                    COD_PRODUCTIVAS -> PRODUCTIVAS
                    COD_CAMBIO_NIVEL -> CAMBIO_NIVEL
                    COD_VENTAS -> VENTAS
                    COD_PEDIDOS -> PEDIDOS
                    else -> NINGUNO
                }
            }
        }
    }
}

fun TipoGrafico.mostrarEnDosLineas(): Boolean {
    return when (this) {
        TipoGrafico.VENTAS, TipoGrafico.PROMEDIO, TipoGrafico.PEDIDOS -> true
        else -> false
    }
}