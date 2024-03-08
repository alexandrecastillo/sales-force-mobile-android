package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.RegionesDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.SeccionesDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.ZonasDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.RegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.SeccionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.ZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.llaveua.LlaveUAMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.RegionGrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.SeccionSeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.SeccionSociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa.ZonaGzMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.RddRegionDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.RddSeccionDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.RddZonaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.unidadadministrativa.UnidadAdministrativaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddRegionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddSeccionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.RddZonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val unidadAdministrativaModule by lazy {
    listByElementsOf<Module>(
        uaModule,
        regionModule,
        zonaModule,
        seccionModule)
}

private val uaModule = module {
    factory { LlaveUAMapper() }
    factory { LlaveUaDbStore() }
    factory<UnidadAdministrativaRepository> {
        UnidadAdministrativaDataRepository(
            planRepository = get(),
            regionGrMapper = get(),
            zonaGzMapper = get(),
            seccionSociaMapper = get(),
            campaniaDataMapper = get(),
            sesionManager = get(),
            llaveUaDbStore = get())
    }
}

private val regionModule = module {
    factory { RegionMapper(focosMapper = get(), habilidadesMapper = get()) }
    factory { RegionesDbDataStore() }
    factory { RegionDBDataStore() }
    factory { RegionGrMapper() }
    factory<RddRegionRepository> { RddRegionDataRepository(localStore = get(), mapper = get()) }
}

private val zonaModule = module {
    factory { ZonaMapper(focosMapper = get(), habilidadesMapper = get()) }
    factory { ZonasDbDataStore() }
    factory { ZonaGzMapper() }
    factory<ZonaDataStore> { ZonaDBDataStore() }
    factory<RddZonaRepository> {
        RddZonaDataRepository(dbDataStore = get(), avanceMapper = get())
    }
}

private val seccionModule = module {
    factory { SeccionMapper(focosMapper = get()) }
    factory { SeccionesDbDataStore() }
    factory { SeccionSociaMapper() }
    factory { SeccionSeMapper() }
    factory { SeccionDBDataStore(seccionAvanceMapper = get()) }
    factory<RddSeccionRepository> { RddSeccionDataRepository(dbStore = get()) }
}
