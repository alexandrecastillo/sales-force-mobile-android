package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos.ComportamientoDetallePorcentajeDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos.ComportamientoDetallePorcentajeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientoDetallePorcentajeRepository

class ComportamientoDetallePorcentajeDataRepository(
    private val dbStore: ComportamientoDetallePorcentajeDbStore,
    private val mapper: ComportamientoDetallePorcentajeMapper
)
    : ComportamientoDetallePorcentajeRepository {

    override fun obtenerPorcentajes(llaveUA: LlaveUA, campania: String): List<ComportamientoPorcentaje> {
        val porcentajes = dbStore.obtenerPorcentajes(llaveUA, campania)
        return mapper.map(porcentajes)
    }

}
