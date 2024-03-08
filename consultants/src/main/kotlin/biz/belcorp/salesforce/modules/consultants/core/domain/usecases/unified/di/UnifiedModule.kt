package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantConfigurationUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetFilterParamsUseCase
import org.koin.dsl.module

val unifiedModule = module {

    factory { GetConsultantsUseCase(get()) }
    factory { GetConsultantConfigurationUseCase(get()) }
    factory { GetFilterParamsUseCase(get(), get()) }
}
