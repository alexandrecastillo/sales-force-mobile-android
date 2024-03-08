package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloCreacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Completable
import java.util.*

open class CambioEventoUseCase(
    val eventoRepository: EventoRepository,
    val campaniaRepository: CampaniasRepository,
    val sesionManager: SessionPersonRepository,
    private val programadorAlarmas: ProgramarAlarmasEventosUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun enviarAServidor() {
        val completable = eventoRepository.enviarAServidor()
        execute(completable, EnvioObserver())
    }

    private inner class EnvioObserver : BaseCompletableObserver() {

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }

    fun validarFechas(modeloCreacion: EventoRddModeloCreacion): Completable {
        return Completable.create {
            modeloCreacion.validarFechaDentroDePeriodo()
            modeloCreacion.validarFechaPosteriorAAhora()
            modeloCreacion.validarFechaInicioMenorAFin()

            it.onComplete()
        }
    }

    private fun EventoRddModeloCreacion.validarFechaInicioMenorAFin() {
        if (fechaFin != null && !esTodoElDia && (fechaInicio.timeInMillis > fechaFin.timeInMillis))
            throw HoraInicioMayorAFinException()
    }

    private fun EventoRddModeloCreacion.validarFechaDentroDePeriodo() {

        val campania = requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa()))
        val finCampania =
            campania.fin?.toCalendar()?.addOneMonth()?.aproximarAUltimoInstanteDelDia()

        finCampania?.let {
            if (fechaInicio > it)
                throw FechaFueraDePeriodoException()
        }
    }

    private fun EventoRddModeloCreacion.validarFechaPosteriorAAhora() {
        if (esTodoElDia) {
            validarSoloFechaPosteriorAHoy()
        } else {
            validarFechaYHoraPosteriorAHoy()
        }
    }

    private fun EventoRddModeloCreacion.validarSoloFechaPosteriorAHoy() {
        if (fechaInicio.dayOfYear < Calendar.getInstance().dayOfYear)
            throw FechaAntesAHoyException()
    }

    private fun EventoRddModeloCreacion.validarFechaYHoraPosteriorAHoy() {
        if (fechaInicio.timeInMillis < Calendar.getInstance().timeInMillis)
            throw FechaAntesAHoyException()
    }

    protected fun programarAlarmas(): Completable {
        return programadorAlarmas.programarAlarmas()
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


    class FechaFueraDePeriodoException : Exception("La fecha está fuera del período")

    class HoraInicioMayorAFinException :
        Exception("La hora de inicio no puede ser mayor a la hora fin")

    class FechaAntesAHoyException : Exception("La fecha no puede ser antes a hoy")
}
