package biz.belcorp.salesforce.core.data.repository.analytics.di

import biz.belcorp.salesforce.core.data.repository.analytics.FirebaseAnalyticsDataRepository
import biz.belcorp.salesforce.core.data.repository.analytics.cloud.FirebaseAnalyticsCloudDataStore
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseAnalyticsRepository
import org.koin.dsl.module

internal val analyticsModule = module {

    factory { FirebaseAnalyticsCloudDataStore(context = get()) }
    factory<FirebaseAnalyticsRepository> { FirebaseAnalyticsDataRepository(analyticsCloudDataStore = get()) }

}
