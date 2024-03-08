package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.MadreLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.MadreUsuarioMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.MadreUsuarioRepository

class MadreUsuarioDataRepository(
    private val localDataStore: MadreLocalDataStore,
    private val mapper: MadreUsuarioMapper
) : MadreUsuarioRepository {

    override fun recuperar(): UsuarioMadre? {
        localDataStore.recuperar()?.run {
            return mapper.parse(this)
        }
        return null
    }
}
