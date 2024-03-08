package biz.belcorp.salesforce.core.domain.usecases.traceability

import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseCrashlyticsRepository
import biz.belcorp.salesforce.core.domain.repository.newrelic.NewRelicRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository

class TraceabilityUseCase(
    private val firebaseCrashlyticsRepository: FirebaseCrashlyticsRepository,
    private val newRelicRepository: NewRelicRepository,
    private val sessionRepository: SessionRepository
) {

    suspend fun setUserKeys() {
        val campaign = sessionRepository.getSession()?.campaign?.codigo.orEmpty()
        firebaseCrashlyticsRepository.setUserKeys(isFull = true)
        newRelicRepository.setUserKeys(campaign)
    }

}
