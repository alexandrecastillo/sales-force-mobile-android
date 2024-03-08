package biz.belcorp.salesforce.messaging.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.messaging.features.sync.NotificationsSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val syncModule = module {

    factory<SyncManager>(named<NotificationsSyncManager>()) {
        NotificationsSyncManager()
    }

}
