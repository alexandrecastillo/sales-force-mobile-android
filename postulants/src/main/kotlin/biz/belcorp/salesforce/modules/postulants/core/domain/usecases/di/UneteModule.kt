package biz.belcorp.salesforce.modules.postulants.core.domain.usecases.di

import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.*
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.SyncPostulantesUseCase
import org.koin.dsl.module

val uneteModule = module {

    factory { PostulantesUseCase(get(), get(), get(), get()) }
    factory { ParametroUneteUseCase(get(), get(), get()) }
    factory { ValidarBloqueosUseCase(get(), get(), get(), get(), get()) }
    factory { TablaLogicaUseCase(get(), get(), get()) }
    factory { TipoMetaUseCase(get(), get(), get()) }
    factory { ObtenerNombreConsultoraRecomendanteUseCase(get(), get(), get(), get()) }
    factory { MapsUseCase(get(), get(), get()) }
    factory { UneteConfiguracionUseCase(get(), get(), get()) }
    factory { IndicadorUseCase(get(), get(), get()) }
    factory { SyncPostulantesUseCase(get(), get(), get()) }

}
