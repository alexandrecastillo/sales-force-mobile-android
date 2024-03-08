package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.HabilidadesDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.cloud.HabilidadesCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadesLiderazgoDetalleMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.MisHabilidadesDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.rol.GerenteRegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.rol.GerenteZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.avance.AvanceHabilidadesRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesAsignadasDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import com.google.gson.Gson
import org.koin.core.module.Module
import org.koin.dsl.module

internal val habilidadesModule by lazy {
    listByElementsOf<Module>(
        habilidadesPersonaModule,
        desarrolloHabilidadesModule,
        avanceHabilidadesModule,
        habilidadesAsignadasModule,
        habilidadesReconocidasModule,
        misHabilidadesModule
    )
}

private val habilidadesPersonaModule = module {
    factory { HabilidadesMapper() }
    factory { HabilidadMapper() }
    factory { HabilidadesDBDataStore(habilidadMapper = get()) }
    factory<HabilidadesRepository> { HabilidadesDataRepository(habilidadesDBStore = get()) }
}

private val desarrolloHabilidadesModule = module {
    factory { DesarrolloHabilidadesDBDataStore() }
    factory<DesarrolloHabilidadesRepository> {
        DesarrolloHabilidadesDataRepository(dataStore = get(), mapper = get())
    }
}

private val avanceHabilidadesModule = module {
    factory { AvanceHabilidadesDBDataStore() }
    factory<AvanceHabilidadesRepository> {
        AvanceHabilidadesDataRepository(
            avanceHabilidadesDataStore = get(),
            habilidadesDataStore = get()
        )
    }
}

private val habilidadesAsignadasModule = module {
    factory { Gson() }
    factory { HabilidadesDbDataStore() }
    factory { HabilidadesLiderazgoDetalleMapper() }
    factory { HabilidadesDetalleDBDataStore(mapper = get()) }
    factory<HabilidadesDetalleRepository> {
        biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.HabilidadesDetalleDBDataStore(
            get()
        )
    }

    factory { GerenteZonaMapper() }
    factory { GerenteRegionMapper() }
    factory<DatosPersonaHabilidadesRepository> {
        DatosPersonaHabilidadesDBDataStore(
            gerenteZonaMapper = get(),
            gerenteRegionMapper = get()
        )
    }
    factory<HabilidadesAsignaRepository> {
        HabilidadesAsignaDBDataStore(gson = get(), mapper = get())
    }
    factory<HabilidadesAsignadasDetalleRepository> {
        HabilidadesAsignadasDetalleDataRepository(
            habilidadesDBDataStore = get(),
            habilidadesAsignaDBDataStore = get(),
            rddPersonaRepository = get(),
            habilidadesDetalleDBDataStore = get()
        )
    }
}

private val habilidadesReconocidasModule = module {
    factory { HabilidadesCloudStore(get(), get()) }
    factory<HabilidadesReconoceRepository> { HabilidadesReconoceDBDataStore(get(), get()) }
    factory<HabilidadesAsyncRepository> { HabilidadesAsyncDataRepository(cloudStore = get()) }
}

private val misHabilidadesModule = module {
    factory { MisHabilidadesDataMapper() }
    factory { MisHabilidadesLocalStore() }
    factory<MisHabilidadesRepository> {
        MisHabilidadesDataRepository(
            localStore = get(),
            mapper = get()
        )
    }
}
