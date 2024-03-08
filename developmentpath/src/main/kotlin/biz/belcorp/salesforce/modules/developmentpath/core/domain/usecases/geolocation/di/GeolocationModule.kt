package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation.GeolocationUseCase
import org.koin.dsl.module

internal val geolocationModule = module {
    factory { GeolocationUseCase(get(), get(), get()) }
}
