package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.DesarrolloHabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DesarrolloHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.DesarrolloHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.DesarrolloHabilidadMapper
import io.reactivex.Single

class DesarrolloHabilidadesDataRepository(
    private val dataStore: DesarrolloHabilidadesDBDataStore,
    private val mapper: DesarrolloHabilidadMapper
) : DesarrolloHabilidadesRepository {

    override fun getDesarrolloHabilidades(llaveUA: LlaveUA): Single<List<DesarrolloHabilidad>> {
        return dataStore.getDesarrolloHabilidades(llaveUA)
            .map { mapper.parseEntityList(it) }
    }

}
