package biz.belcorp.salesforce.core.data.repository.firebase.di

import biz.belcorp.salesforce.core.data.repository.firebase.CloudMessagingDataRepository
import biz.belcorp.salesforce.core.data.repository.firebase.FirebaseCrashlyticsDataRepository
import biz.belcorp.salesforce.core.data.repository.firebase.RemoteConfigDataRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseCrashlyticsRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository
import org.koin.dsl.module


val firebaseModule = module {

    factory<CloudMessagingRepository> { CloudMessagingDataRepository(get(), get()) }
    factory<RemoteConfigRepository> { RemoteConfigDataRepository(get(), get(), get(), get(),get()) }
    factory<FirebaseCrashlyticsRepository> { FirebaseCrashlyticsDataRepository(get(), get()) }

}
