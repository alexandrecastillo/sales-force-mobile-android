package biz.belcorp.salesforce.core.domain.usecases.firebase

import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseCrashlyticsRepository

class FirebaseCrashlyticsUseCase(
    private val firebaseCrashlyticsRepository: FirebaseCrashlyticsRepository
) {

    suspend fun setUserKeys() {
        firebaseCrashlyticsRepository.setUserKeys(isFull = true)
    }

}
