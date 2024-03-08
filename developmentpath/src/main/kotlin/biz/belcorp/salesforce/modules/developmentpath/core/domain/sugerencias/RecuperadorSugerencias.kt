package biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorRdd


class RecuperadorSugerencias {

    fun obtenerSugerencias(buscables: List<BuscadorRdd.Buscable>): List<String> {
        return buscables
            .mapNotNull { it.sugerir() }
            .distinctBy { it }
            .sortedBy { it }
    }
}
