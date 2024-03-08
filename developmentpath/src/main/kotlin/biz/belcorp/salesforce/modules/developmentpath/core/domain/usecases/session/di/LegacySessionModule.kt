package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.ObtenerSesionPersonaUseCase
import org.koin.dsl.module

val legacySesionModule = module {

    factory { ObtenerSesionPersonaUseCase(get()) }

}
