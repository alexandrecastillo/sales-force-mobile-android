package biz.belcorp.salesforce.core.connectivity


sealed class ConnectivityState {

    object Online : ConnectivityState()
    object Offline : ConnectivityState()

}
