package biz.belcorp.salesforce.modules.brightpath.core.data.mapper.di

import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.RegionEntityDataMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.SectionEntityDataMapper
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua.ZoneEntityDataMapper
import org.koin.dsl.module

val mapperModule = module {

    factory { RegionEntityDataMapper() }
    factory { ZoneEntityDataMapper() }
    factory { SectionEntityDataMapper() }

}
