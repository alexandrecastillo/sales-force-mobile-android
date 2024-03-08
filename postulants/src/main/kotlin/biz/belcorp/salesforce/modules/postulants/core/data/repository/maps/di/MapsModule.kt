package biz.belcorp.salesforce.modules.postulants.core.data.repository.maps.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.maps.GoogleCloudStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.maps.GoogleMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.maps.MapsDataRepository
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.MapsRepository
import org.koin.dsl.module

internal val mapsModule = module {

    factory { GoogleMapper() }

    factory { GoogleCloudStore(get(), get()) }

    factory { MapsDataRepository(get()) as MapsRepository }
}
