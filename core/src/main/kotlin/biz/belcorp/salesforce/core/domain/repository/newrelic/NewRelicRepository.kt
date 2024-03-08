package biz.belcorp.salesforce.core.domain.repository.newrelic

interface NewRelicRepository {
    suspend fun setUserKeys(campaign: String)
}
