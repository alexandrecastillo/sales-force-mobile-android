package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import java.util.*

class CreacionAcuerdosUseCase(private val visitaRepository: RddPersonaRepository,
                              threadExecutor: ThreadExecutor,
                              postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    companion object {
        const val CANTIDAD_MAXIMA_ACUERDOS = 50
    }

    private val acuerdos = mutableListOf<Acuerdo.ModeloCreacion>()

    fun listarCreados(): List<Acuerdo.ModeloCreacion> {
        return acuerdos
    }

    fun crear(request: CrearRequest) {
        val single = Single.create<Acuerdo.ModeloCreacion> { emitter ->
            validarLimiteDeAcuerdos()

            val persona = requireNotNull(visitaRepository.recuperarVisita(request.visitaId)?.persona)
            val nuevo = Acuerdo.ModeloCreacion(request.contenido,
                persona.unidadAdministrativa.campania.codigo,
                persona.llaveUA,
                Date()
            )

            acuerdos.add(0, nuevo)

            emitter.onSuccess(nuevo)
        }

        execute(single, request.subscriber)
    }

    private fun validarLimiteDeAcuerdos() {
        if (acuerdos.size >= CANTIDAD_MAXIMA_ACUERDOS) {
            throw Exception("Límite de acuerdos ($CANTIDAD_MAXIMA_ACUERDOS) alcanzado")
        }
    }

    fun eliminar(request: EliminarRequest) {
        val single = Single.create<Int> { emitter ->
            acuerdos.removeAt(request.posicion)

            emitter.onSuccess(request.posicion)
        }
        execute(single, request.subscriber)
    }

    class CrearRequest(val visitaId: Long,
                       val contenido: String,
                       val subscriber: SingleObserver<Acuerdo.ModeloCreacion>
    )

    class EliminarRequest(val posicion: Int,
                          val subscriber: SingleObserver<Int>
    )
}
