package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct

class TopSoldProductsModelMapper(private val textResolver: TopSoldProductsTextResolver) {

    fun parse(items: List<TopSoldProduct>) = items.map { parse(it, it.top == NUMBER_ONE) }

    private fun parse(product: TopSoldProduct, isTopSold: Boolean) = with(product) {
        return@with TopSoldProductCampaignModel(
            productName = textResolver.parsePosition(top, productName),
            isTopSold = isTopSold,
            average = average.toString(),
            campaigns = getCampaignProducts(histories)
        )
    }

    private fun getCampaignProducts(campaigns: List<TopSoldProduct.History>): List<TopSoldProductCampaignModel.CampaignModel> {
        return campaigns.map { parseCampaign(it) }.sortedBy { it.name }
    }

    private fun parseCampaign(history: TopSoldProduct.History) =
        TopSoldProductCampaignModel.CampaignModel(
            name = history.campaign,
            value = history.average.toString()
    )


}
