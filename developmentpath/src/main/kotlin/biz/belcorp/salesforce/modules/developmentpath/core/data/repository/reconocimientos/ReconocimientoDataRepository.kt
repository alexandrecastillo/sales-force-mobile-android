package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud.ReconocimientoCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.ReconocimientoLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.ReconocimientoDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository
import io.reactivex.Completable
import io.reactivex.Single

class ReconocimientoDataRepository(
    private val localStore: ReconocimientoLocalStore,
    private val cloudStore: ReconocimientoCloudStore,
    private val mapper: ReconocimientoDataMapper
) : ReconocimientoRepository {

    override suspend fun saveRecognition(recognition: ReconocimientoASuperior) {
        localStore.saveRecognition(mapper.parse(recognition))
    }

    override fun recuperarLista(): Single<List<ReconocimientoASuperior>> {
        return localStore.recuperarLista()
            .map { mapper.parse(it) }
    }

    override fun recuperar(idReconocimiento: Long): Single<ReconocimientoASuperior> {
        return localStore.recuperar(idReconocimiento)
            .map { mapper.parse(it) }
    }

    override fun reconocer(reconocimiento: ReconocimientoAGrabar): Completable {
        return localStore.reconocer(mapper.parse(reconocimiento))
    }

    override fun sincronizar(): Completable {
        return localStore.recuperarPendientes()
            .filter { it.isNotEmpty() }
            .map { mapper.parseToRequest(it) }
            .flatMapCompletable { cloudStore.enviar(it) }
            .andThen(localStore.marcarAEnviados())
    }
}
