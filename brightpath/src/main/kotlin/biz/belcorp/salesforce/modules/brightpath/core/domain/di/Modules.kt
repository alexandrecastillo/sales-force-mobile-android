package biz.belcorp.salesforce.modules.brightpath.core.domain.di

import biz.belcorp.salesforce.modules.brightpath.core.domain.sync.SyncBrightPathUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.BrightPathIndicatorUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.BusinessPartnerChangeLevelUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.DrillDownUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.GetBusinessPartnerChangeLevelUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.consultants.ConsultantsUseCase
import biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.ua.UASegmentUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { BrightPathIndicatorUseCase(get(), get(), get()) }
    factory { DrillDownUseCase(get(), get(), get(), get()) }
    factory { UASegmentUseCase(get(), get(), get(), get()) }
    factory { SyncBrightPathUseCase(get(), get()) }
    factory { ConsultantsUseCase(get()) }

    factory { BusinessPartnerChangeLevelUseCase(get(), get(), get()) }
    factory { GetBusinessPartnerChangeLevelUseCase(get()) }


}
