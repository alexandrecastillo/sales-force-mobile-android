package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje

interface ComportamientoDetallePorcentajeRepository {

    fun obtenerPorcentajes(llaveUA: LlaveUA, campania: String): List<ComportamientoPorcentaje>

}
