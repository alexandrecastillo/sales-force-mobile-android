package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoConMadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.MadreUsuarioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class ManageReconcocimientosUseCase(
    private val reconocimientosRepository: ReconocimientoRepository,
    private val madreUsuarioRepository: MadreUsuarioRepository,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtenerReconocimiento(
        idReconocimiento: Long,
        subscriber: BaseSingleObserver<ReconocimientoConMadre>
    ) {
        val single = reconocimientosRepository.recuperar(idReconocimiento)
            .flatMap { recuperarConUaDePersonaAReconocer(it) }
            .doInParallel(obtenerMadre())
            .map { ReconocimientoConMadre(it.second, it.first) }

        execute(single, subscriber)
    }

    private fun recuperarConUaDePersonaAReconocer(reconocimiento: ReconocimientoASuperior): Single<ReconocimientoASuperior> {
        return doOnSingle {
            val sesion = requireNotNull(sesionManager.get())
            reconocimiento.uACodigo = obtenerUaPorRol(sesion, reconocimiento.rolReconocida)
            reconocimiento
        }
    }

    private fun obtenerUaPorRol(sesion: Sesion, rolAReconocer: Rol): String {
        return when (rolAReconocer) {
            Rol.DIRECTOR_VENTAS -> sesion.pais?.codigoIso ?: "-"
            Rol.GERENTE_REGION -> sesion.region ?: "-"
            Rol.GERENTE_ZONA -> sesion.zona ?: "-"
            else -> throw UnsupportedRoleException(rolAReconocer)
        }
    }

    fun reconocer(reconocimiento: ReconocimientoAGrabar, subscriber: BaseCompletableObserver) {
        val completable = reconocimientosRepository.reconocer(reconocimiento)
            .doAfterTerminate { sincronizar() }

        execute(completable, subscriber)
    }

    private fun obtenerMadre(): Single<UsuarioMadre> {
        return doOnSingle {
            requireNotNull(madreUsuarioRepository.recuperar())
        }
    }

    private fun sincronizar() {
        val completable = reconocimientosRepository.sincronizar()
        execute(completable, SincronizacionObserver())
    }

    private class SincronizacionObserver : BaseCompletableObserver() {

        override fun onError(e: Throwable) = e.printStackTrace()
    }
}
