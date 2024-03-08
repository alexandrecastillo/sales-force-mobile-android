package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.anotaciones

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.cloud.AnotacionCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import io.reactivex.Completable

open class AnotacionesDataRepository(val storeDB: AnotacionDBStore,
                                     val storeCloud: AnotacionCloudStore
) {

    fun sincronizarEliminados(): Completable {
        return storeDB.recuperarEliminadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarEliminados(it).toMaybe() }
            .flatMapCompletable { storeDB.marcarEliminadosComoEnviados() }
    }

    fun sincronizarEditados(): Completable {
        return storeDB.recuperarEditadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarEditados(it).toMaybe() }
            .flatMapCompletable { storeDB.marcarEditadosComoEnviados() }
    }

    fun sincronizarNuevos(): Completable {
        return storeDB.recuperarNuevosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarNuevos(it).toMaybe() }
            .flatMapCompletable { storeDB.marcarNuevosComoEnviados(it.resultado) }
    }
}
