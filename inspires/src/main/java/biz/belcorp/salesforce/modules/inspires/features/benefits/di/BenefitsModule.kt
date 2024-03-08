package biz.belcorp.salesforce.modules.inspires.features.benefits.di

import biz.belcorp.salesforce.modules.inspires.features.benefits.InspireBenefitsPresenter
import org.koin.dsl.module

internal val benefitsModule = module {

    factory { InspireBenefitsPresenter(get(), get()) }

}
