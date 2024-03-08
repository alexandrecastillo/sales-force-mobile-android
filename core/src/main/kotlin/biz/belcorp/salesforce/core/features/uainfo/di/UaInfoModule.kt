package biz.belcorp.salesforce.core.features.uainfo.di

import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import org.koin.dsl.module


internal val uaInfoModule = module {

    factory { UaInfoMapper(get()) }

}
