package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar.RegistrarPresenter
import org.koin.dsl.module

internal val registrarModule = module {
    factory { RegistrarPresenter(get(),get(),get(),get()) }
}
