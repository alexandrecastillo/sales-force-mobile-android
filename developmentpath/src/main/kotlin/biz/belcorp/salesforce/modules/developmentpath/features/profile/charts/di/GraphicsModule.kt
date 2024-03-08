package biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.mappers.RankingChartsMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.utils.RankingChartUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.charts.ranking.view.RankingChartsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val graphicsModule = module {
    viewModel { RankingChartsViewModel(get(), get()) }
    factory { RankingChartsMapper() }
    factory {
        RankingChartUtil(
            get()
        )
    }
}
