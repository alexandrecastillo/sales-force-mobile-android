package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos.ComportamientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos.ComportamientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientosRepository

class ComportamientosDataRepository(
    private val comportamientosDbStore: ComportamientosDbStore,
    private val comportamientoMapper: ComportamientoMapper
) : ComportamientosRepository {

    override fun obtenerPorRol(rol: Rol): List<Comportamiento> {
        val modelos = comportamientosDbStore.obtener(rol)
        return comportamientoMapper.convertir(modelos)
    }
}
