package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import io.reactivex.Single

class ObtenerNombreConsultoraRecomendanteUseCase(
    private val postulantesRepository: PostulantesRepository,
    private val sesionManager: ObtenerSesionUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    val sesion get() = sesionManager.obtener()

    fun obtener(codigoConsultora: String, subscriber: BaseSingleObserver<String>) {
        val single = if (verificarEnSesion(codigoConsultora)) {
            Single.just(sesion.person.firstName)
        } else {
            postulantesRepository.obtenerNombreConsultora(sesion.countryIso, codigoConsultora)
        }
        execute(single, subscriber)
    }

    private fun esColombia() = sesion.pais == Pais.COLOMBIA

    private fun esCodigoDeSesion(codigoConsultora: String) =
        sesion.codigoUsuario == codigoConsultora

    private fun esDocumentoDeSesion(numeroDocumento: String) =
        sesion.person.document == numeroDocumento

    private fun verificarEnSesion(valor: String) =
        if (esColombia()) esDocumentoDeSesion(valor) else esCodigoDeSesion(valor)
}
