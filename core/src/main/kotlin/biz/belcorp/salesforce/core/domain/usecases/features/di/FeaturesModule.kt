package biz.belcorp.salesforce.core.domain.usecases.features.di

import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import org.koin.dsl.module


internal val dynamicFeatureModule = module {

    factory { GetFeaturesUseCase(get()) }

}
