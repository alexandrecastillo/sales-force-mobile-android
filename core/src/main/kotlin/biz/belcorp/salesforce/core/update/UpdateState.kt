package biz.belcorp.salesforce.core.update

sealed class UpdateState {

    object Downloaded : UpdateState()

    object UpdateAvailable : UpdateState()

}
