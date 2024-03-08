package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import biz.belcorp.salesforce.core.utils.esPadreDirectoDe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesAsignadasDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniasRddUseCase

class ObtenerHabilidadesDetalleUseCase(
    private val habilidadesAsignadasDetalleRepository: HabilidadesAsignadasDetalleRepository,
    private val obtenerCampaniasUseCase: ObtenerCampaniasRddUseCase,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    companion object {
        const val DETALLE = 1
        const val COMENTARIO = 2
    }

    private lateinit var habilidadesLiderazgo: List<DetalleHabilidadesAcompaniamiento>
    private lateinit var tipoDetalle: TipoDetalle
    private var habilitarAsignacion = false

    fun obtener(request: Request, subscriber: BaseSingleObserver<Response>) {
        val single = obtenerCampaniasUseCase
            .recuperarCampaniaActual(request.personaId, request.rol)
            .flatMap { campania ->
                habilidadesAsignadasDetalleRepository
                    .obtener(request.personaId, request.rol, campania.codigo)
                    .map {
                        habilidadesLiderazgo = it
                        tipoDetalle = obtenerTipoDetalle(request.rol)
                        habilitarAsignacion = seDebeHabilitarAsignacion(request)
                        obtenerResponse()
                    }
            }

        execute(single, subscriber)
    }

    private fun seDebeHabilitarAsignacion(request: Request): Boolean {
        return habilidadesLiderazgo.isEmpty() && elRolLogueadoPuedeAsignar(request)
    }

    private fun elRolLogueadoPuedeAsignar(request: Request): Boolean {
        val sesion = requireNotNull(sesionManager.get())

        return sesion.rol esPadreDirectoDe request.rol
    }

    private fun obtenerResponse(): Response {
        return Response(
            habilidadesLiderazgo = habilidadesLiderazgo,
            mostrarSinHabilidades = habilidadesLiderazgo.isEmpty(),
            habilitarAsignacion = habilitarAsignacion,
            mostrarDetalleHabilidades = habilidadesLiderazgo.isNotEmpty(),
            tipoDetalle = tipoDetalle
        )
    }

    private fun obtenerTipoDetalle(rol: Rol): TipoDetalle {
        return if (rol == Rol.GERENTE_REGION) {
            TipoDetalle.DETALLE_PARA_GR
        } else {
            TipoDetalle.DETALLE_PARA_GZ
        }
    }

    class Request(
        val personaId: Long,
        val rol: Rol
    )

    class Response(
        val habilidadesLiderazgo: List<DetalleHabilidadesAcompaniamiento>,
        val mostrarSinHabilidades: Boolean,
        val habilitarAsignacion: Boolean,
        val tipoDetalle: TipoDetalle,
        val mostrarDetalleHabilidades: Boolean
    )

    enum class TipoDetalle(val codigo: Int) {
        DETALLE_PARA_GZ(1), DETALLE_PARA_GR(2)
    }
}
