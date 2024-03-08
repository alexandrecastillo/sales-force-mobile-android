package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import io.reactivex.Completable
import io.reactivex.Single

interface ReconocimientoRepository {
    suspend fun saveRecognition(recognition: ReconocimientoASuperior)
    fun recuperarLista(): Single<List<ReconocimientoASuperior>>
    fun recuperar(idReconocimiento: Long): Single<ReconocimientoASuperior>
    fun reconocer(reconocimiento: ReconocimientoAGrabar): Completable
    fun sincronizar(): Completable
}
