package biz.belcorp.salesforce.core.domain.usecases.sync

sealed class SyncType {

    object None : SyncType()

    open class Updated : SyncType()

    class FullyUpdated : Updated()

    class PartiallyUpdated : Updated()
}

