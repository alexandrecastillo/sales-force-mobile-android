package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.cloud.RutaOptimaCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.ConfiguracionRddDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.FechaNoHabilDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.IntencionPedidoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta.RutaOptimaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.ConfigRddRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.FechaNoHabilRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.RutaOptimaRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val rutaModule by lazy {
    listByElementsOf<Module>(
        rutaRDDModule,
        configuracionModule,
        rutaOptimaModule,
        intencionPedidoModule,
        configuracionRddModule,
        fechaNoHabilModule
    )
}

private val rutaRDDModule = module {
    factory { SenderCambioRDD(context = get()) }
    factory { DetalleRutaMapper() }
}

private val configuracionModule = module {
    factory { ConfiguracionRutaMapper() }
    factory { ConfiguracionRutaDBDataStore(mapper = get()) }
}

private val rutaOptimaModule = module {
    factory { RespuestaRutaMapper() }
    factory { RutaOptimaMapper() }
    factory { RutaOptimaCloudStore(googleApi = get(), mapper = get()) }
    factory { RutaOptimaDBDataStore(mapper = get()) }
    factory<RutaOptimaRepository> {
        RutaOptimaDataRepository(
            rutaCloudStore = get(),
            rutaDBD = get(),
            configuracionDB = get(),
            rutaOptimaMapper = get()
        )
    }
}

private val intencionPedidoModule = module {
    factory { IntencionPedidoMapper() }
    factory { IntencionPedidoDb() }
    factory<IntencionPedidoRepository> {
        IntencionPedidoDataRepository(
            mapper = get(),
            intencionPedidoDb = get(),
            syncRestApi = get(),
            llaveUAMapper = get(),
            apiCallHelper = get()
        )
    }
}

private val configuracionRddModule = module {
    factory { VisitasPorFechaMapper() }
    factory<ConfigRddRepository> { ConfiguracionRddDataRepository(dbStore = get()) }
}

private val fechaNoHabilModule = module {
    factory { FechaNoHabilMapper() }
    factory { FechaNoHabilDBDataStore(fechaNoHabilMapper = get()) }
    factory<FechaNoHabilRepository> { FechaNoHabilDataRepository(dbStore = get()) }
}
