package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud.ReconocimientoCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.cloud.ReconocimientosCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.MadreLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.ReconocimientoLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.reconocimiento.data.ReconocimientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.MadreUsuarioMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.ReconocimientoDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.reconocimientos.ReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.MadreUsuarioDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.ReconocimientoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.ReconocimientosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.reconocimientos.ReconocimientosEnCampaniaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.MadreUsuarioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosEnCampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientosRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val reconocimientoModule by lazy {
    listByElementsOf<Module>(
        reconocimientosDataModule,
        reconocimientosAMadreModule)
}

private val reconocimientosDataModule = module {
    factory { ReconocimientoMapper() }
    factory { ReconocimientosDbStore() }
    factory { ReconocimientosCloudStore(syncRestApi = get(), apiCallHelper = get()) }
    factory { ReconocimientoLocalStore(mapper = get()) }
    factory { ReconocimientoCloudStore(get(), get()) }
    factory { ReconocimientoDataMapper() }

    factory<ReconocimientoRepository> {
        ReconocimientoDataRepository(localStore = get(), cloudStore = get(), mapper = get())
    }

    factory<ReconocimientosRepository> {
        ReconocimientosDataRepository(
            reconocimientoMapper = get(),
            dbStore = get(),
            cloudStore = get())
    }

    factory<ReconocimientosEnCampaniaRepository> {
        ReconocimientosEnCampaniaDataRepository(get(), get(), get(), get(), get())
    }
}


private val reconocimientosAMadreModule = module {
    factory { MadreLocalDataStore() }
    factory { MadreUsuarioMapper() }
    factory<MadreUsuarioRepository> { MadreUsuarioDataRepository(localDataStore = get(), mapper = get()) }
}
