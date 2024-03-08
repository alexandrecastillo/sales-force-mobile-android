package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas

data class CatalogSaleConsultant(
    val consultantCode: String,
    val campaign: String,
    val amount: Double,
    val amountBilledOrders: Double,
    val amountAverageSaleLastSixCampaigns: Double,
    val isHighValueOrder: Boolean,
    val billingFirstCampaign: String,
    val billingLastCampaign: String
)
