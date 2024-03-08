package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

sealed class DigitalSaleViewState {
    class Success(val container: DigitalSaleContainerModel) : DigitalSaleViewState()
    object Empty : DigitalSaleViewState()
    class Error(val message: String) : DigitalSaleViewState()
}
