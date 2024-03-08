package biz.belcorp.salesforce.messaging.features.list.di

import biz.belcorp.salesforce.messaging.features.list.mappers.NotificationMapper
import biz.belcorp.salesforce.messaging.features.list.view.NotificationTextResolver
import biz.belcorp.salesforce.messaging.features.list.view.NotificationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val messagingListModule = module {
    factory { NotificationTextResolver(get()) }
    viewModel {
        NotificationViewModel(
            get(),
            get(),
            get()
        )
    }
    factory { NotificationMapper(get()) }
}
