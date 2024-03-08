package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.FocosHabilidadesEnDashboardRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class FocosHabilidadesEquipoUseCase(
    private val listadoFocosHabilidadesEnDashboardRepository: FocosHabilidadesEnDashboardRepository,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(subscriber: SingleObserver<Response>) {
        val observable = Single.create<Response> { emitter ->
            val sesion = requireNotNull(sesionManager.get())
            val response = recuperarUAsComoRespuesta(sesion.rol)

            emitter.onSuccess(response)
        }

        execute(observable, subscriber)
    }

    private fun recuperarUAsComoRespuesta(rolSesion: Rol): Response {
        return when (rolSesion) {
            Rol.GERENTE_REGION -> Response.Zonas(recuperarZonas())
            Rol.DIRECTOR_VENTAS -> Response.Regiones(recuperarRegiones())
            else -> throw UnsupportedRoleException(rolSesion)
        }
    }

    fun obtenerRol(): String {
        val rol = requireNotNull(sesionManager.get().persona)
        return rol.rol.codigoRol
    }

    private fun recuperarZonas(): List<ZonaRdd> {
        return listadoFocosHabilidadesEnDashboardRepository.obtenerZonas()
    }

    private fun recuperarRegiones(): List<RegionRdd> {
        return listadoFocosHabilidadesEnDashboardRepository.obtenerRegiones()
    }

    sealed class Response {
        class Zonas(val zonas: List<ZonaRdd>) : Response()

        class Regiones(val regiones: List<RegionRdd>) : Response()
    }
}
