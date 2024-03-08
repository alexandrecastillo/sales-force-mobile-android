package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.cloud.AcuerdosCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdosLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import io.reactivex.Completable

class AcuerdosDataRepository(private val storeLocal: AcuerdosLocalDataStore,
                             private val storeCloud: AcuerdosCloudDataStore
) : AcuerdosRepository {

    override fun obtener(unidadAdministrativa: LlaveUA): List<Acuerdo> {
        return storeLocal.obtener(unidadAdministrativa)
    }

    override fun obtenerUltimasCampanias(unidadAdministrativa: LlaveUA, cantidadCampanias: Int): List<Acuerdo> {
        return storeLocal.obtenerCampaniasCXMenos1(unidadAdministrativa, cantidadCampanias)
    }

    override fun obtenerParaCampaniaActual(unidadAdministrativa: LlaveUA): List<Acuerdo> {
        return storeLocal.obtenerParaCampaniaActual(unidadAdministrativa)
    }


    override fun obtener(acuerdoId: Long): Acuerdo? {
        return storeLocal.obtener(acuerdoId)
    }

    override fun insertar(acuerdos: List<Acuerdo.ModeloCreacion>) {
        storeLocal.insertar(acuerdos)
        sincronizar().subscribe({}, {})
    }

    override fun editar(acuerdo: Acuerdo) {
        storeLocal.guardar(acuerdo)
        sincronizar().subscribe({}, {})
    }

    override fun eliminar(id: Long) {
        storeLocal.eliminar(id)
        sincronizar().subscribe({}, {})
    }

    override fun sincronizar(): Completable {
        return Completable.merge(listOf(
            sincronizarEliminados(),
            sincronizarEditados(),
            sincronizarNuevos()))
    }

    private fun sincronizarEditados(): Completable {
        return storeLocal.recuperarEditadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarEditados(it).toMaybe() }
            .flatMapCompletable { storeLocal.marcarEditadosComoEnviados() }
    }

    private fun sincronizarEliminados(): Completable {
        return storeLocal.recuperarEliminadosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarEliminados(it).toMaybe() }
            .flatMapCompletable { storeLocal.marcarEliminadosComoEnviados() }
    }

    private fun sincronizarNuevos(): Completable {
        return storeLocal.recuperarNuevosNoEnviados()
            .filter { it.isNotEmpty() }
            .flatMap { storeCloud.enviarNuevos(it).toMaybe() }
            .flatMapCompletable { storeLocal.marcarNuevosComoEnviados(it.resultado) }
    }
}
