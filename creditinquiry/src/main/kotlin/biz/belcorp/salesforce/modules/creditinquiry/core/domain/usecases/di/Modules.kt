package biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases.di

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases.ConsultaCrediticiaUseCase
import org.koin.dsl.module

val creditInquiryModule = module {

    factory { ConsultaCrediticiaUseCase(get(), get(), get(), get()) }

}
