package biz.belcorp.salesforce.messaging.features.news.di

import android.util.DisplayMetrics
import biz.belcorp.salesforce.messaging.features.news.NewsViewModel
import biz.belcorp.salesforce.messaging.features.news.mapper.NewsMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DENSITY_DPI = "DENSITY_DPI"

val newsModule = module {

    factory(named(DENSITY_DPI)) { get<DisplayMetrics>().densityDpi }

    factory { NewsMapper(get(named(DENSITY_DPI))) }

    viewModel { NewsViewModel(get(), get()) }
}
