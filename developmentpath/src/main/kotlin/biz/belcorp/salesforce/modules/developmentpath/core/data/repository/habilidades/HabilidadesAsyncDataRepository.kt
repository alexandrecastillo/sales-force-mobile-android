package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.cloud.HabilidadesCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesAsyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import io.reactivex.Completable

class HabilidadesAsyncDataRepository(private val cloudStore: HabilidadesCloudStore) :
    HabilidadesAsyncRepository {

    override fun guardarHabilidadesAsignadas(asignadas: List<HabilidadesAsignaRepository.DetalleHabilidad>)
        : Completable {
        return cloudStore.guardarHabilidadesAsignadas(asignadas)
    }

    override fun guardarHabilidadesReconocidas(reconocidas: List<HabilidadesReconoceRepository.HabilidadReconocida>)
        : Completable {
        return cloudStore.guardarHabilidadesReconocidas(reconocidas)
    }
}
