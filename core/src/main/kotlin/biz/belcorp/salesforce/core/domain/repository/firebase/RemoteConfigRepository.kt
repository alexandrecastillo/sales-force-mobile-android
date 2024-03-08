package biz.belcorp.salesforce.core.domain.repository.firebase


interface RemoteConfigRepository {

    suspend fun fetchConfig()

}
