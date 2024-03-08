package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

open class SellingDataViewState {
    class Failed(val message: String) : SellingDataViewState()

    class SellingData(val data: Pair<String, String>) : SellingDataViewState()
}
