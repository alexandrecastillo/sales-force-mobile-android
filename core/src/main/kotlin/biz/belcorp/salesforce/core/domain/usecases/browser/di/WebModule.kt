package biz.belcorp.salesforce.core.domain.usecases.browser.di

import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import org.koin.dsl.module

val webModule = module {

    factory { GetWebUrlUseCase(get(), get(), get()) }

}
