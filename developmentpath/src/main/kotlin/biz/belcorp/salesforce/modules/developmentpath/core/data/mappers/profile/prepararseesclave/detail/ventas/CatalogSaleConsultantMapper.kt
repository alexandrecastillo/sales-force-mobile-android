package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.CatalogSaleConsultant

class CatalogSaleConsultantMapper {

    fun map(entity: ConsultantSaleEntity, consultant: ConsultantEntity?) =
        CatalogSaleConsultant(
            consultantCode = entity.consultantCode,
            amount = entity.catalogSale,
            campaign = entity.campaign,
            amountAverageSaleLastSixCampaigns = entity.averageSaleLastSixCampaigns,
            isHighValueOrder = entity.isHighOrderValue,
            amountBilledOrders = entity.billedOrderAmount,
            billingFirstCampaign = consultant?.billingFirstCampaign.orEmpty(),
            billingLastCampaign = consultant?.billingLastCampaign.orEmpty()
        )

    fun map(
        entities: List<ConsultantSaleEntity>,
        consultant: ConsultantEntity?
    ): List<CatalogSaleConsultant> {
        return entities.map { map(it, consultant) }
    }
}
