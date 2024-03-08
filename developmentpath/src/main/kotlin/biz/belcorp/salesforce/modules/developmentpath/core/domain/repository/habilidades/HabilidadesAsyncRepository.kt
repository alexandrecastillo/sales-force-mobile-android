package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import io.reactivex.Completable

interface HabilidadesAsyncRepository {
    fun guardarHabilidadesAsignadas(asignadas: List<HabilidadesAsignaRepository.DetalleHabilidad>): Completable
    fun guardarHabilidadesReconocidas(reconocidas: List<HabilidadesReconoceRepository.HabilidadReconocida>): Completable
}
