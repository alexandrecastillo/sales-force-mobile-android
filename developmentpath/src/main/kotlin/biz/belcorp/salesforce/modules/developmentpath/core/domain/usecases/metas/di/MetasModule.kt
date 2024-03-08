package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.*
import org.koin.dsl.module

internal val metasModule = module {
    factory { MetaPersonalUseCase(get(), get(), get(), get()) }
    factory { TipoMetaUseCase(get(), get(), get()) }
    factory { MetaSociaUseCase(get(), get(), get(), get()) }
    factory { AdministrarMetaConsultoraUseCase(get(), get(), get()) }
    factory { ObtenerListadoCampanaFinUseCase(get(), get(), get(), get()) }
}
