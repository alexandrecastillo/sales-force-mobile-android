package biz.belcorp.salesforce.modules.consultants.features.quantity


sealed class ConsultantsQuantityViewState {

    class Success(val quantity: Int, val buttonEnabled: Boolean) : ConsultantsQuantityViewState()

    class Failed(val message: String) : ConsultantsQuantityViewState()

    object HideLoading : ConsultantsQuantityViewState()
}
