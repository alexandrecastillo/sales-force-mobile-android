package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.EstructuraFocosHabilidades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Regiones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Secciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Zonas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single

class DeterminarFocosParaRol(
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(subscriber: BaseSingleObserver<EstructuraFocosHabilidades>) {
        val single = recuperarRol().map { decidirFocos(it) }

        execute(single, subscriber)
    }

    private fun recuperarRol(): Single<Rol> {
        return Single.create {
            val rol = requireNotNull(sesionManager.get()?.rol)
            it.onSuccess(rol)
        }
    }

    private fun decidirFocos(rol: Rol): EstructuraFocosHabilidades {
        return when (rol) {
            Rol.DIRECTOR_VENTAS ->
                EstructuraFocosHabilidades(
                    verMisFocos = true,
                    verMisHabilidades = false,
                    focosHabilidadesUa = Regiones()
                )
            Rol.GERENTE_REGION ->
                EstructuraFocosHabilidades(
                    verMisFocos = true,
                    verMisHabilidades = true,
                    focosHabilidadesUa = Zonas()
                )
            Rol.GERENTE_ZONA ->
                EstructuraFocosHabilidades(
                    verMisFocos = true,
                    verMisHabilidades = true,
                    focosHabilidadesUa = Secciones()
                )
            else -> error("Rol no soportado")
        }
    }
}
