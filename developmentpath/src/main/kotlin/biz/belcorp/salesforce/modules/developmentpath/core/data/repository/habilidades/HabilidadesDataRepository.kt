package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.HabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesRepository

class HabilidadesDataRepository(private val habilidadesDBStore: HabilidadesDBDataStore) :
    HabilidadesRepository {
    override fun obtener(rol: Rol): List<Habilidad> {
        return habilidadesDBStore.obtener(rol)
    }
}
