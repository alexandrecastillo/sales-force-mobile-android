package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.*
import org.koin.dsl.module

internal val focosModule = module {
    factory { AsignarFocosUseCase(get(), get(), get(), get(), get(), get(), get()) }
    factory { DeterminarFocosParaRol(get(), get(), get()) }
    factory { FocosHabilidadesEquipoUseCase(get(), get(), get(), get()) }
    factory { FocosZonaUseCase(get(), get(), get()) }
    factory { FocoUseCase(get(), get(), get()) }
    factory { ObtenerFocosUseCase(get(), get(), get(), get()) }
    factory { PermitirAutoasignacionFocosUseCase(get(), get(), get()) }
    factory { RecuperarMisFocosUseCase(get(), get(), get(), get()) }
    factory { SincronizadorFocos(get(), get()) }
}
