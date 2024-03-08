package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import io.reactivex.Completable
import io.reactivex.Single

interface IngresosExtraRepository {
    fun obtenerMarcas(ua: LlaveUA, personaId: Long): Single<List<OtraMarca>>
    fun actualizarMarca(model: OtraMarca): Completable
    fun actualizarMarcaList(data: List<OtraMarca>): Completable
    fun sincronizar(): Completable
}
