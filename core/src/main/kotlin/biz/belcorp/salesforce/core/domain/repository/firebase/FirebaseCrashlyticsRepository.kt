package biz.belcorp.salesforce.core.domain.repository.firebase

interface FirebaseCrashlyticsRepository {
    suspend fun setUserKeys(isFull: Boolean)
}
