package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.differenceInDays
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultadoVisitasRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import java.util.*

class RecuperarResultadoVisitasUseCase(
    private val campaniaRepository: CampaniasRepository,
    private val sesionManager: SessionPersonRepository,
    private val resultadoVisitasRepository: ResultadoVisitasRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    companion object {
        // A partir del segundo día
        const val DIAS_DESPUES_INICIO_FACTURACION = 1
    }

    fun ejecutar(subscriber: SingleObserver<Response>) {
        val single = Single.create<Response> { emitter ->
            if (pasaronNDiasDespuesDeFacturacion(DIAS_DESPUES_INICIO_FACTURACION)) {
                emitter.onSuccess(obtenerResponseActivo())
            } else {
                emitter.onSuccess(Response.Inactivo())
            }
        }
        execute(single, subscriber)
    }

    private fun pasaronNDiasDespuesDeFacturacion(diasDespues: Int): Boolean {
        val campaniaActual =
            checkNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa()))
        var diferenciaEntreHoyEInicioFacturacion = 0

        campaniaActual.inicioFacturacion?.let {
            diferenciaEntreHoyEInicioFacturacion = Date() differenceInDays it
        }

        return diferenciaEntreHoyEInicioFacturacion >= diasDespues
    }

    private fun obtenerResponseActivo(): Response.Activo {
        return Response.Activo(
            consultorasQueFacturaron =
            resultadoVisitasRepository.recuperarCantidadConsultorasFacturadas(),
            consultorasQueNoFacturaron =
            resultadoVisitasRepository.recuperarCantidadConsultorasNoFacturadas()
        )
    }

    private fun recuperarLlaveUa(): LlaveUA {
        val sesion = requireNotNull(sesionManager.get())

        return LlaveUA(
            codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
            codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
            codigoSeccion = sesion.seccion.aGuionSiEsNullOVacio(),
            consultoraId = null
        )
    }


    sealed class Response {
        class Inactivo : Response()

        data class Activo(
            val consultorasQueFacturaron: Long,
            val consultorasQueNoFacturaron: Long
        ) : Response()
    }
}
