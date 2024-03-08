package biz.belcorp.salesforce.core.domain.usecases.campania.di

import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import org.koin.dsl.module

val campaniaModule = module {

    factory { ObtenerCampaniasUseCase(get(), get(), get(), get()) }

}
