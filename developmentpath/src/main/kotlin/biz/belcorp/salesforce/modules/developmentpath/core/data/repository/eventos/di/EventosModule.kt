package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud.EventosDataCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.cloud.EventosXUaDataCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.AdministradorEventosXUaCambiados
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.EventosDataLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.EventosXUaDataLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.TiposEventoRddDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.EventoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.EventoRddXUaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos.TiposEventoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.AlertasDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.EventoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.EventoXUaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.eventos.TiposEventoRddDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.AlertasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoXUaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.TiposEventoRddRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val eventosModule by lazy {
    listByElementsOf<Module>(
        eventosDataModule,
        eventosUAModule,
        alertasModule,
        tiposEventoModule
    )
}

private val eventosDataModule = module {
    factory { EventosDataLocalStore(eventoXUaLocalStore = get()) }
    factory { EventosDataCloudStore(get(), get()) }
    factory { EventoRddMapper() }
    factory<EventoRepository> {
        EventoDataRepository(
            localStore = get(),
            cloudStore = get(),
            syncPreferences = get(),
            mapper = get()
        )
    }
}

private val eventosUAModule = module {

    factory { AdministradorEventosXUaCambiados() }
    factory { EventosXUaDataLocalStore(administrador = get()) }
    factory { EventosXUaDataCloudStore(get(), get()) }
    factory { EventoRddXUaMapper() }
    factory<EventoXUaRepository> {
        EventoXUaDataRepository(localStore = get(), cloudStore = get(), mapper = get())
    }
}

private val alertasModule = module {
    factory<AlertasRepository> { AlertasDataRepository() }
}

private val tiposEventoModule = module {
    factory { TiposEventoRddMapper() }
    factory { TiposEventoRddDataStore() }
    factory<TiposEventoRddRepository> {
        TiposEventoRddDataRepository(
            dbStore = get(),
            mapper = get()
        )
    }
}
