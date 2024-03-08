package biz.belcorp.salesforce.modules.consultants.core.data.repository.location.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.cloud.GeoLocationCloudDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.data.GeoLocationDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.location.data.GeoLocationDataStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.location.GeoLocationRepository
import org.koin.dsl.module

val geoLocationModule = module {

    factory { GeoLocationDataStore() }
    factory { GeoLocationCloudDataStore(get()) }
    factory<GeoLocationRepository> { GeoLocationDataRepository(get(), get()) }

}
