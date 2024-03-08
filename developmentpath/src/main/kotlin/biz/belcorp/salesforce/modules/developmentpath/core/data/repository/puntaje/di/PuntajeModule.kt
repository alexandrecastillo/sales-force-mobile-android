package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.cloud.PuntajePremioCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.data.PuntajeReconocimientoLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.puntaje.PuntajeReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje.PuntajePremioDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje.PuntajeReconocimientoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajePremioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajeReconocimientoRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val puntajeModule by lazy {
    listByElementsOf<Module>(
        puntajePremioModule,
        puntajeReconocimientoModule
    )
}

private val puntajePremioModule = module {
    factory { PuntajePremioCloudDataStore(syncApi = get(), apiCallHelper = get()) }
    factory<PuntajePremioRepository> { PuntajePremioDataRepository(cloudStore = get()) }
}

private val puntajeReconocimientoModule = module {

    factory { PuntajeReconocimientoMapper() }
    factory { PuntajeReconocimientoLocalDataStore() }
    factory<PuntajeReconocimientoRepository> {
        PuntajeReconocimientoDataRepository(puntajesReconoce = get(), mapper = get())
    }
}
