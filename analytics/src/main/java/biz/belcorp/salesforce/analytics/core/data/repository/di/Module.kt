package biz.belcorp.salesforce.analytics.core.data.repository.di

import biz.belcorp.salesforce.analytics.core.data.repository.FirebaseAnalyticsDataRepository
import biz.belcorp.salesforce.analytics.core.data.repository.LogLocalDataRepository
import biz.belcorp.salesforce.analytics.core.data.repository.cloud.FirebaseAnalyticsCloudDataStore
import biz.belcorp.salesforce.analytics.core.domain.repository.FirebaseAnalyticsRepository
import biz.belcorp.salesforce.analytics.core.domain.repository.LogLocalRepository
import org.koin.dsl.module

internal val firebaseRepository = module {
    factory { FirebaseAnalyticsCloudDataStore(get()) }
    factory<FirebaseAnalyticsRepository> { FirebaseAnalyticsDataRepository(get()) }
    factory<LogLocalRepository> { LogLocalDataRepository(get()) }
}
