package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

data class TopSoldProductCampaignModel(
    val productName: String,
    val average: String,
    val isTopSold: Boolean,
    val campaigns: List<CampaignModel>
) {
    data class CampaignModel(
        val name: String,
        val value: String
    )
}
