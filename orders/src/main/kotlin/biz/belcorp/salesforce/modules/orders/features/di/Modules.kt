package biz.belcorp.salesforce.modules.orders.features.di

import biz.belcorp.salesforce.modules.orders.features.results.di.resultsModule
import biz.belcorp.salesforce.modules.orders.features.search.di.searchModule


val featureModules by lazy {
    listOf(
        resultsModule,
        searchModule
    )
}
