package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos

open class TopSoldProductsViewState {

    class Success(val data: List<TopSoldProductCampaignModel>) : TopSoldProductsViewState()

    class Failed(val message: String) : TopSoldProductsViewState()

}
