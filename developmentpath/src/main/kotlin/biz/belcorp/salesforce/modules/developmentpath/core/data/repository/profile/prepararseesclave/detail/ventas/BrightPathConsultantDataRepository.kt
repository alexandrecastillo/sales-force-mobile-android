package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.SaleBrightPathConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.BrightPathSaleRepository

class BrightPathConsultantDataRepository(
    private val SaleBrightPathConsultantDataStore: SaleBrightPathConsultantDataStore,
) : BrightPathSaleRepository {

    override suspend fun getSaleBrightPathConsultant(
        consultantCode: String,
        campaigns: List<String>
    ): List<ConsultantSaleEntity> {
        return SaleBrightPathConsultantDataStore.getSaleBrightPathConsultant(consultantCode, campaigns)
    }
}
