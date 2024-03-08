package biz.belcorp.salesforce.base.core.domain.usecases.options.di

import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuSubOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetShortcutOptionsUseCase
import org.koin.dsl.module

val optionsModule = module {

    factory { GetMenuOptionsUseCase(get()) }
    factory { GetMenuSubOptionsUseCase(get()) }
    factory { GetShortcutOptionsUseCase(get(), get()) }

}
