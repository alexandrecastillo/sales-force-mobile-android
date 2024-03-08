package biz.belcorp.salesforce.modules.brightpath.core.domain.repository

interface ConsultantsRepository {
    suspend fun getConsultantsMayChangeLevelListSize(section: String): Int
}
