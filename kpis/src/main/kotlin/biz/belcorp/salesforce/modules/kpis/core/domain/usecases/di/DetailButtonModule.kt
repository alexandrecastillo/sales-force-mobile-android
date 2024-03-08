package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.detailbutton.GetDetailButtonUseCase
import org.koin.dsl.module

val detailButtonModule = module {
    factory { GetDetailButtonUseCase(get(), get()) }
}
