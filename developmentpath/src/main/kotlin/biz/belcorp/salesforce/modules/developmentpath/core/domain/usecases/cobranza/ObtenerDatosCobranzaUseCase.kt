package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaRepository

class ObtenerDatosCobranzaUseCase(
    private val datosCobranzaRepository: DatosCobranzaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(personaId: Long, rol: Rol, subscriber: BaseSingleObserver<DatosCobranza>) {
        val single = datosCobranzaRepository
            .recuperar(personaId, rol)
        execute(single, subscriber)
    }

    data class DatosCobranza(val ventaGanancia: String,
                             val saldoPendiente: String,
                             val consultoraConsecutiva: String,
                             val ventaFacturada: String,
                             val recaudoComisionable: String,
                             val ganancia: String,
                             val recaudoTotal: String,
                             val recaudoNoComisionable: String,
                             val gananciaVentaRetail: String,
                             val ventaRetail: String)
}
