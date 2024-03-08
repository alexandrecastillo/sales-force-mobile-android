package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos

import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud.ReconocimientosCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.ReconocimientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.ReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toArrayOf
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import io.reactivex.Completable

class ReconocimientosDataRepository(
    private val reconocimientoMapper: ReconocimientoMapper,
    private val dbStore: ReconocimientosDbStore,
    private val cloudStore: ReconocimientosCloudStore
) :
    ReconocimientosRepository {

    override fun guardar(request: ReconocimientosRepository.GuardarRequest) {
        dbStore.guardar(reconocimientoMapper.map(request))
    }

    override fun contarReconocimientos(personaId: Long, codigoCampania: String): Long {
        val reconocimientos = dbStore.obtnerReconocimientosCampania(personaId, codigoCampania)
        val idsReconocidos = reconocimientos?.comportamiento?.toArrayOf<Long>()
        return idsReconocidos?.size?.toLong() ?: 0L
    }

    override fun sincronizar(): Completable {
        return doOnSingle { dbStore.obtenerPendientesDeEnvio() }
            .flatMapCompletable {
                if (it.isEmpty()) {
                    Completable.complete()
                } else {
                    cloudStore.subirReconocimientos(it)
                        .andThen(doOnCompletable { dbStore.ponerTodosComoEnviado(it) })
                }
            }
    }

}
