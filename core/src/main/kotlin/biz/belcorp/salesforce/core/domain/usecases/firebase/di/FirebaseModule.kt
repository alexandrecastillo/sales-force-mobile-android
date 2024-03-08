package biz.belcorp.salesforce.core.domain.usecases.firebase.di

import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FirebaseAnalyticsUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FirebaseCrashlyticsUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import org.koin.dsl.module

val firebaseModule = module {
    factory { ManageTopicsUseCase(get(), get()) }
    factory { FirebaseAnalyticsUseCase(get(), get(), get(), get(), get()) }
    factory { FetchRemoteConfigUseCase(get()) }
    factory { FirebaseCrashlyticsUseCase(get()) }
}
