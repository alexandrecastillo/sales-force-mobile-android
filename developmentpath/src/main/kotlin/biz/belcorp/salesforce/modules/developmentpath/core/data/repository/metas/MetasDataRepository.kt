package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud.MetaSociaCloudDataStore
import io.reactivex.Completable

open class MetasDataRepository(
    private val cloudStore: MetaSociaCloudDataStore,
    private val dbStore: AnotacionDBStore
) {

    fun sincronizarEliminados(): Completable {
        return dbStore.recuperarEliminadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.eliminar(it).toMaybe() }
            .flatMapCompletable { dbStore.marcarEliminadosComoEnviados() }
            .onErrorResumeNext { Completable.complete() }
    }

    fun sincronizarEditados(): Completable {
        return dbStore.recuperarEditadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.actualizar(it).toMaybe() }
            .flatMapCompletable { dbStore.marcarEditadosComoEnviados() }
            .onErrorResumeNext { Completable.complete() }
    }

    fun sincronizarNuevos(): Completable {
        return dbStore.recuperarNuevosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.guardar(it).toMaybe() }
            .flatMapCompletable { dbStore.marcarNuevosComoEnviados(it.resultado) }
            .onErrorResumeNext { Completable.complete() }
    }
}
