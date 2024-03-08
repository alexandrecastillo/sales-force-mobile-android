package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.nivel.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.nivel.UaKey
import org.koin.dsl.module

val levelModule = module {

    factory { UaKey(get()) }

}
