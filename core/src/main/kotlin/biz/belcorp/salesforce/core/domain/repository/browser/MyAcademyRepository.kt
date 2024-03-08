package biz.belcorp.salesforce.core.domain.repository.browser


interface MyAcademyRepository {

    suspend fun getUrl(document: String, country: String): String

}
