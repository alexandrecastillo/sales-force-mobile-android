package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class CargaInicialRegistroVisitaUseCase(
    private val visitaRepository: RddPersonaRepository,
    private val intencionPedidoRepository: IntencionPedidoRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(visitaId: Long, subscriber: SingleObserver<Response>) {
        val single = Single.create<Response> { emitter ->
            val visita = recuperarVisita(visitaId)
            val persona = visita.persona
            val mostrarPasaPedido = mostrarPasaPedidoSegunRol(persona)
            val pasaPedido = if (mostrarPasaPedido) recuperarIntencionExistenteOPorDefecto(persona) else mostrarPasaPedido
            val mostrarAcuerdos = mostrarAcuerdosSegunRol(persona)
            emitter.onSuccess(
                Response(
                    mostrarAcuerdos = mostrarAcuerdos,
                    mostrarPasaPedido = mostrarPasaPedido,
                    pasaPedido = pasaPedido,
                    persona = persona,
                    visita = visita
                )
            )
        }
        execute(single, subscriber)
    }

    private fun recuperarIntencionExistenteOPorDefecto(persona: Persona): Boolean {
        return intencionPedidoRepository.recuperar(persona.llaveUA) ?: false
    }


    private fun recuperarVisita(visitaId: Long): Visita {
        return requireNotNull(visitaRepository.recuperarVisita(visitaId)) {
            "No se pudo recuperar visita con id $visitaId"
        }
    }

    private fun mostrarAcuerdosSegunRol(persona: Persona): Boolean {
        return persona !is PosibleConsultoraRdd
    }

    private fun mostrarPasaPedidoSegunRol(persona: Persona): Boolean {
        return persona is ConsultoraRdd
    }

    class Response(
        val mostrarAcuerdos: Boolean,
        val mostrarPasaPedido: Boolean,
        val pasaPedido: Boolean,
        val persona: Persona,
        val visita: Visita
    )
}
