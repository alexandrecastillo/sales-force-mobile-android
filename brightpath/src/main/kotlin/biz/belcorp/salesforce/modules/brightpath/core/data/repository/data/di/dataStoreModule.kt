package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.di

import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath.BrightPathIndicatorDataStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.consultants.ConsultantsDataStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.ua.UaSegmentsDBDataStore
import org.koin.dsl.module

val dataStoreModule = module {

    factory { BrightPathIndicatorDataStore() }
    factory { UaSegmentsDBDataStore() }
    factory { ConsultantsDataStore() }

}
