package biz.belcorp.salesforce.messaging.core.data.repository.di

import biz.belcorp.salesforce.messaging.core.data.repository.NotificationMapper
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.NotificationCloudStore
import biz.belcorp.salesforce.messaging.core.data.repository.data.NotificationsDataRepository
import biz.belcorp.salesforce.messaging.core.data.repository.data.NotificationsDataStore
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository
import org.koin.dsl.module

internal val notificationsModule = module {

    factory { NotificationMapper() }

    factory { NotificationCloudStore(get()) }
    factory { NotificationsDataStore() }

    factory<NotificationsRepository> { NotificationsDataRepository(get(), get(), get(), get()) }
}
