package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity

interface BrightPathSaleRepository {
    suspend fun getSaleBrightPathConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<ConsultantSaleEntity>
}
