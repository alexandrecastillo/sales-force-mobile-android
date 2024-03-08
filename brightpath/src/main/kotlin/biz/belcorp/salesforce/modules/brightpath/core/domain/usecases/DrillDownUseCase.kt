package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver

class DrillDownUseCase constructor(
    private val campaignRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    private val uaKey = LlaveUA(
        codigoRegion = session.region,
        codigoZona = session.zona,
        codigoSeccion = session.seccion,
        consultoraId = null
    )

    fun getCampaignPeriod(observer: BaseSingleObserver<Campania>) {
        val single = campaignRepository.obtenerCampaniaActualSingle(uaKey)

        execute(single, observer)
    }

}
