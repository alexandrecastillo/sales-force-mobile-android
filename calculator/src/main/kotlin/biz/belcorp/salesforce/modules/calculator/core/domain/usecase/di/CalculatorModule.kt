package biz.belcorp.salesforce.modules.calculator.core.domain.usecase.di

import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.GetCampaignProjectionInfoUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.GetResultsLastCampaignUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SaveCampaignProjectionInfoUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SyncPartnerUseCase
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SyncUseCase
import org.koin.dsl.module

val calculatorModule = module {
    factory { SyncUseCase(get(), get()) }
    factory { SyncPartnerUseCase(get(), get()) }
    factory { GetCampaignProjectionInfoUseCase(get(), get()) }
    factory { SaveCampaignProjectionInfoUseCase(get(), get()) }
    factory { GetResultsLastCampaignUseCase(get()) }
}
