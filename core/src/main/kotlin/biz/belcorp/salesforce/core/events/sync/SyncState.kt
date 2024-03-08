package biz.belcorp.salesforce.core.events.sync


sealed class SyncState {

    object Started : SyncState()
    object Updated : SyncState()
    object None : SyncState()
    class Failed(val throwable: Throwable) : SyncState()

}
