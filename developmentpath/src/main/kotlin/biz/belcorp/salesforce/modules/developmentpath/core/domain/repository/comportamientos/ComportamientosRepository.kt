package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento

interface ComportamientosRepository {
    fun obtenerPorRol(rol: Rol): List<Comportamiento>
}
