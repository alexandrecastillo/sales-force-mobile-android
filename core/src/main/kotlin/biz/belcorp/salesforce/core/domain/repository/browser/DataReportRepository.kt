package biz.belcorp.salesforce.core.domain.repository.browser


interface DataReportRepository {

    suspend fun getUrlForSE(): String

    suspend fun getUrlForGZ(): String

}
