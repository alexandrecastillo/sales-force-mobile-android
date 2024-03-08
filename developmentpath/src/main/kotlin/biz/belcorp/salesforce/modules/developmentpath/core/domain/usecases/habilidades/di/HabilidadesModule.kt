package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.*
import org.koin.dsl.module

internal val habilidadesModule = module {
    factory { AsignarHabilidadesUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { GetAvanceHabilidadesUseCase(get(), get(), get(), get()) }
    factory { GetDesarrolloHabilidadesUseCase(get(), get(), get(), get(), get()) }
    factory { ObtenerHabilidadesDetalleUseCase(get(), get(), get(), get(), get()) }
    factory { PermitirAutoasignacionHabilidadesUseCase(get(), get(), get()) }
    factory { ReconocerHabilidadesUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { RecuperarMisHabilidadesUseCase(get(), get(), get(), get(), get()) }
    factory { SincronizadorAsignarHabilidades(get(), get()) }
    factory { SincronizadorReconocerHabilidades(get(), get()) }
}
