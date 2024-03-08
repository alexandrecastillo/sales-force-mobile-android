package biz.belcorp.salesforce.modules.billing.di

import biz.belcorp.salesforce.core.di.features.DependenciesProvider
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.core.utils.loadModules
import biz.belcorp.salesforce.modules.billing.core.data.di.dataModule
import biz.belcorp.salesforce.modules.billing.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.billing.core.domain.di.domainModule
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.di.rejectedOrdersUseCase
import biz.belcorp.salesforce.modules.billing.features.di.featureModules
import org.koin.core.module.Module


@Suppress("unused")
class FeatureDependenciesProvider : DependenciesProvider {

    override fun init() {
        loadModules(billingFeatureModules)
    }

}

internal val billingFeatureModules by lazy {
    listByElementsOf<Module>(
        networkModule,
        dataModule,
        domainModule,
        rejectedOrdersUseCase,
        featureModules
    )
}

