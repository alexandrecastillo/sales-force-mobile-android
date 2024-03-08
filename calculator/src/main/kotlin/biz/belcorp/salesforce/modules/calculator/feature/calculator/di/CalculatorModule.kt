package biz.belcorp.salesforce.modules.calculator.feature.calculator.di

import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CampaignProjectionModelMapper
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CampaignProjectionViewModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection.ProgressProjectionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val calculatorModule = module {

    factory { CampaignProjectionModelMapper() }
    viewModel {
        CampaignProjectionViewModel(
            getCampaignProjectionInfoUseCase = get(),
            saveCampaignProjectionInfoUseCase = get(),
            getConfigurationUseCase = get(),
            getSaleForceStatusUseCase = get(),
            getResultsLastCampaignUseCase = get(),
            mapper = get(),
            syncPartner = get()
        )
    }
    viewModel {
        ProgressProjectionViewModel(
            getCampaignProjectionInfoUseCase = get(),
            getConfigurationUseCase = get(),
            get(),
            get(),
            mapper = get(),
            syncPartner = get()
        )
    }
}
