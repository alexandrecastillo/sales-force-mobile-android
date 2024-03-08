package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.geolocation.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation.GeoLocationDBdataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.geolocation.GeolocationCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.geolocation.GeolocationDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.geolocation.GeolocationRepository
import org.koin.dsl.module

internal val geolocationModule = module {
    factory { GeolocationCloudDataStore(get()) }
    factory { GeoLocationDBdataStore() }
    factory<GeolocationRepository> { GeolocationDataRepository(get(), get(), get()) }
}
