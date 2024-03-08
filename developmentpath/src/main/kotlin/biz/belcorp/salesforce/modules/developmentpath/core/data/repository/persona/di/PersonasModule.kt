package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion.GrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion.GzMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion.SociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.perfil.PosibleConsultoraMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.ConsultoraRDDMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.GerenteRegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.GerenteZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas.SociaEmpresariaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.*
import org.koin.core.module.Module
import org.koin.dsl.module

internal val personasModule by lazy {
    listByElementsOf<Module>(
        personaModule,
        consultorasModule,
        sociasEmpresariasModule,
        gerentesZonaModule,
        gerentesRegionModule,
        posibleConsultoraModule
    )
}


private val personaModule = module {

    factory<SessionPersonRepository> { SessionPersonDataRepository(get(), get(), get(), get()) }

    factory { VisitaRddDBDataStore(visitaMapper = get(), planRutaDB = get()) }
    factory { VisitaMapper() }

    factory<RddPersonaRepository> {
        PersonaDataRepository(
            posibleConsultoraDB = get(),
            consultoraDB = get(),
            sociaEmpresariaDB = get(),
            gerenteZonaDBDataStore = get(),
            grDBDataStore = get(),
            visitaDB = get(),
            planRddRepository = get()
        )
    }
}

private val consultorasModule = module {
    factory { ConsultoraDBDataStore(visitaRddDBDataStore = get(), consultoraRDDMapper = get()) }
    factory<ConsultoraRDDRepository> { ConsultoraDataRepository(dbStore = get()) }
    factory { ConsultoraRDDMapper(visitaMapper = get()) }
}

private val sociasEmpresariasModule = module {
    factory { SociaMapper() }
    factory { SociaEmpresariaDBDataStore(sociaEmpresariaMapper = get()) }
    factory { SociaEmpresariaMapper(visitaRddDBDataStore = get()) }
    factory<SociaEmpresariaRepository> { SociaEmpresariaDataRepository(sociaDataStore = get()) }
}

private val gerentesRegionModule = module {
    factory { GrMapper() }
    factory { GerenteRegionDBDataStore(get(), get()) }
    factory { GerenteRegionMapper() }
    factory<GerenteRegionRepository> { GerenteRegionDataRepository(visitaMapper = get()) }
}

private val gerentesZonaModule = module {
    factory { GzMapper() }
    factory<GerenteZonaDataStore> { GerenteZonaDBDataStore(gerenteZonaMapper = get()) }
    factory { GerenteZonaMapper(visitaMapper = get(), visitaDataStore = get()) }
    factory<GerenteZonaRepository> { GerenteZonaDataRepository(dbStore = get()) }
}

private val posibleConsultoraModule = module {
    factory { PosibleConsultoraMapper(get()) }
    factory { PostulanteDBDataStore(get()) }
    factory<PosibleConsultoraRepository> { PosibleConsultoraDataRepository(dbStore = get()) }
}
