package biz.belcorp.salesforce.modules.calculator.core.data.network.di

import biz.belcorp.salesforce.core.data.network.di.NAMED_BELCORP
import biz.belcorp.salesforce.modules.calculator.core.data.network.CalculatorApi
import biz.belcorp.salesforce.modules.calculator.core.data.network.CampaignProjectionApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


internal val networkModule = module {
    single { provideCampaignProjectionApi(get((named(NAMED_BELCORP)))) }
}

private fun provideCalculatorApi(retrofit: Retrofit): CalculatorApi {
    return retrofit.create(CalculatorApi::class.java)
}

private fun provideCampaignProjectionApi(retrofit: Retrofit): CampaignProjectionApi {
    return retrofit.create(CampaignProjectionApi::class.java)
}
