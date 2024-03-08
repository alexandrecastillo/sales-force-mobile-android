package biz.belcorp.salesforce.messaging.core.domain.usecases.di

import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import biz.belcorp.salesforce.messaging.core.domain.usecases.SaveNotificationUseCase
import biz.belcorp.salesforce.messaging.core.domain.usecases.SyncNotificationsUseCase
import biz.belcorp.salesforce.messaging.core.domain.usecases.UpdateNotificationUseCase
import org.koin.dsl.module


internal val notificationsModule = module {

    factory { SaveNotificationUseCase(get()) }
    factory { GetNotificationUseCase(get()) }
    factory { UpdateNotificationUseCase(get(), get()) }
    factory { SyncNotificationsUseCase(get(), get()) }

}
