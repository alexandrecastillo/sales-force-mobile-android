package biz.belcorp.salesforce.modules.brightpath.core.data.repository.di

import biz.belcorp.salesforce.modules.brightpath.core.data.repository.BrightPathIndicatorDataRepository
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.BusinessPartnerChangeLevelRepository
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.ConsultantsDataRepository
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.UaSegmentsDataRepository
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.BrightPathIndicatorRepository
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.ConsultantsRepository
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.UaSegmentsRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory<BrightPathIndicatorRepository> { BrightPathIndicatorDataRepository(get(), get(), get()) }
    factory<ConsultantsRepository> { ConsultantsDataRepository(get()) }
    factory<UaSegmentsRepository> {
        UaSegmentsDataRepository(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory<BusinessPartnerChangeLevelRepository> { BusinessPartnerChangeLevelRepository(get(), get(), get()) }

}
