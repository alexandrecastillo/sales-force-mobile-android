package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.config

import biz.belcorp.salesforce.core.utils.es
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.parametros.ParametrosRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import java.util.*

class Configurador(
    val parametros: ParametrosRdd,
    private val visitasPorFechas: List<VisitasPorFecha>
) {

    fun obtenerCantidadVisitasPorFecha(fechaBusqueda: Calendar): Int {
        val visitas = visitasPorFechas.find { it.fecha.es(fechaBusqueda) }?.visitas
        return visitas ?: parametros.maxVisitasPorDia
    }
}
