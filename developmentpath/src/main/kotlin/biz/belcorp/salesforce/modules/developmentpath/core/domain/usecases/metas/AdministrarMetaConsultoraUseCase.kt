package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.formatLong
import biz.belcorp.salesforce.core.utils.string
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaConsultora
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaConsultoraRepository
import java.util.*

class AdministrarMetaConsultoraUseCase(
    private val metaConsultoraRepository: MetaConsultoraRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtener(personaId: Long, subscriber: BaseSingleObserver<ObtenerResponse>) {

        val single = metaConsultoraRepository
            .obtenerMeta(personaId.toInt())
            .map { metas -> crearResponse(metas) }

        execute(single, subscriber)
    }

    private fun crearResponse(metas: List<MetaConsultora>) =
        ObtenerResponse(
            metas = metas,
            sePuedeCrear = metas.isEmpty()
        )

    fun guardar(request: GuardarRequest) {
        val repoRequest = obtenerMetaConsultora(request)
        val single = metaConsultoraRepository.guardar(repoRequest)

        execute(single, request.subscriber)
    }

    private fun obtenerMetaConsultora(request: GuardarRequest): MetaConsultora {
        return MetaConsultora(
            request.personaId.toInt(),
            request.idTipoMeta,
            request.descripcionMeta,
            request.comentario,
            request.monto,
            request.campaniaInicio,
            request.campanaFin,
            checkNotNull(Date().string(formatLong))
        )
    }

    data class ObtenerResponse(
        val metas: List<MetaConsultora>,
        val sePuedeCrear: Boolean
    )

    data class GuardarRequest(
        val personaId: Long,
        val idTipoMeta: Int,
        val descripcionMeta: String,
        val comentario: String,
        val monto: String,
        val campaniaInicio: String,
        val campanaFin: String,
        val subscriber: BaseCompletableObserver
    )
}
