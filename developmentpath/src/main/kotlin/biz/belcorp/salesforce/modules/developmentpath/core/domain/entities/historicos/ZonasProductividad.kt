package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad.Builder.Companion.COD_CRITICA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad.Builder.Companion.COD_ESTABLE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad.Builder.Companion.COD_NINGUNO
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad.Builder.Companion.COD_PRODUCTIVA

class ZonasProductividad(
    val campanias: List<String>,
    val zonasProductividad: List<ZonaProductividad>
) {

    enum class Productividad(val abreviacion: String) {
        PRODUCTIVA(COD_PRODUCTIVA),
        ESTABLE(COD_ESTABLE),
        CRITICA(COD_CRITICA),
        NINGUNO(COD_NINGUNO)
    }

    class Builder {
        companion object {

            const val COD_PRODUCTIVA = "P"
            const val COD_ESTABLE = "E"
            const val COD_CRITICA = "C"
            const val COD_NINGUNO = "-"

            fun construir(codigo: String): Productividad {
                return when (codigo) {
                    COD_PRODUCTIVA -> Productividad.PRODUCTIVA
                    COD_ESTABLE -> Productividad.ESTABLE
                    COD_CRITICA -> Productividad.CRITICA
                    else -> Productividad.NINGUNO
                }
            }
        }
    }

    class ZonaProductividad(
        val zona: String,
        val productividadUltimasCampanias: List<Productividad>
    )

}
