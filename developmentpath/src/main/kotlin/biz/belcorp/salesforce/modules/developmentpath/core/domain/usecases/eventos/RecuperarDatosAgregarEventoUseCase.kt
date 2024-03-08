package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.AlertasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.TiposEventoRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import io.reactivex.Single
import io.reactivex.SingleObserver

class RecuperarDatosAgregarEventoUseCase(
    private val planRepository: RddPlanRepository,
    private val tiposEventoRddRepository: TiposEventoRddRepository,
    private val alertasRepository: AlertasRepository,
    private val eventoRepository: EventoRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private lateinit var seleccionadorTiposEvento: Seleccionador<TipoEventoRdd>
    private lateinit var seleccionadorAlertas: Seleccionador<Alerta?>
    private lateinit var rolSeleccionado: Rol
    private var eventoSeleccionado: EventoRdd? = null

    fun ejecutar(request: Request) {
        val single = Single.create<Response> { emitter ->
            recuperarEvento(request)
            recuperarRol(request)
            recuperarTiposEvento()
            recuperarAlertas()
            seleccionarTipoDeEventoCargado()
            seleccionarAlertaCargada()

            emitter.onSuccess(construirResponse())
        }

        execute(single, request.subscriber)
    }

    private fun recuperarEvento(request: Request) {
        if (request is Request.APartirDeEvento) {
            eventoSeleccionado = eventoRepository.recuperarSincrono(request.eventoId)
        }
    }

    private fun recuperarRol(request: Request) {
        rolSeleccionado = when (request) {
            is Request.APartirDePlan ->
                requireNotNull(planRepository.obtenerInfoPlanRdd(request.planId)?.rol)
            is Request.APartirDeEvento ->
                requireNotNull(eventoSeleccionado?.tipo?.rol)
        }
    }

    private fun recuperarTiposEvento() {
        val tiposEvento = tiposEventoRddRepository.recuperar(rolSeleccionado)
        seleccionadorTiposEvento = Seleccionador(tiposEvento)
    }

    private fun seleccionarTipoDeEventoCargado() {
        if (eventoSeleccionado != null) {
            val tipoSeleccionado = seleccionadorTiposEvento.seleccionables.first {
                it.elemento.id == eventoSeleccionado?.tipo?.id
            }

            seleccionadorTiposEvento.seleccionarSolo(tipoSeleccionado)
        } else {
            seleccionadorTiposEvento.seleccionarSoloPrimerElemento()
        }
    }

    private fun recuperarAlertas() {
        val alertas = alertasRepository.recuperar()
        seleccionadorAlertas = Seleccionador(alertas)
    }

    private fun seleccionarAlertaCargada() {
        if (eventoSeleccionado != null) {
            val tipoSeleccionado = seleccionadorAlertas.seleccionables.first {
                it.elemento?.unidad == eventoSeleccionado?.alerta?.unidad &&
                    it.elemento?.valor == eventoSeleccionado?.alerta?.valor
            }

            seleccionadorAlertas.seleccionarSolo(tipoSeleccionado)
        } else {
            seleccionadorAlertas.seleccionarSoloPrimerElemento()
        }
    }

    private fun construirResponse(): Response {
        return Response(
            seleccionadorTiposEvento,
            seleccionadorAlertas,
            eventoSeleccionado,
            rolSeleccionado
        )
    }

    sealed class Request(val subscriber: SingleObserver<Response>) {

        class APartirDePlan(
            val planId: Long,
            subscriber: SingleObserver<Response>
        ) : Request(subscriber)

        class APartirDeEvento(
            val eventoId: Long,
            subscriber: SingleObserver<Response>
        ) : Request(subscriber)
    }

    class Response(
        val seleccionadorTiposEvento: Seleccionador<TipoEventoRdd>,
        val seleccionadorAlertas: Seleccionador<Alerta?>,
        val eventoRdd: EventoRdd?,
        val rol: Rol
    )
}
