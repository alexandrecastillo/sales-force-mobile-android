package biz.belcorp.salesforce.core.data.repository.browser.di

import biz.belcorp.salesforce.core.data.repository.browser.DataReportDataRepository
import biz.belcorp.salesforce.core.data.repository.browser.MyAcademyDataRepository
import biz.belcorp.salesforce.core.data.repository.browser.cloud.MyAcademyCloudStore
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import org.koin.dsl.module

internal val browserModule = module {

    factory { MyAcademyCloudStore(get()) }

    factory<DataReportRepository> {
        DataReportDataRepository(
            get()
        )
    }

    factory<MyAcademyRepository> { MyAcademyDataRepository(get(), get()) }

}
