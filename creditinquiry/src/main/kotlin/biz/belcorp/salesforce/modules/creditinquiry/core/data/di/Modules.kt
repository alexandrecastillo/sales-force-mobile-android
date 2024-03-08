package biz.belcorp.salesforce.modules.creditinquiry.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper.di.mapperModules
import biz.belcorp.salesforce.modules.creditinquiry.core.data.network.di.netWorkModule
import biz.belcorp.salesforce.modules.creditinquiry.core.data.repository.di.repositoryModules
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        mapperModules,
        netWorkModule,
        repositoryModules
    )
}
