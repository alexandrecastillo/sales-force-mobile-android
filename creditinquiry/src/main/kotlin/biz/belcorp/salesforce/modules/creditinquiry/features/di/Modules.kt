package biz.belcorp.salesforce.modules.creditinquiry.features.di

import biz.belcorp.salesforce.modules.creditinquiry.features.mapper.ConsultaCrediticiaModelDataMapper
import biz.belcorp.salesforce.modules.creditinquiry.features.presenters.ConsultaCrediticiaPresenter
import org.koin.dsl.module

val featureModules = module {

    factory { ConsultaCrediticiaModelDataMapper() }

    factory { ConsultaCrediticiaPresenter(get(), get(), get()) }

}
