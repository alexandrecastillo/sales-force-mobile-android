package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.location.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.location.GeoLocationUseCase
import org.koin.dsl.module

val domainMapModule = module {
    factory { GeoLocationUseCase(get()) }
}
