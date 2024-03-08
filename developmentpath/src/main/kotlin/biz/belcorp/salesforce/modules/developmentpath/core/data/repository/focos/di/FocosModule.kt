package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.cloud.FocosCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.cloud.GuardadoFocosCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.FocoDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.GuardadoFocosDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.FocosDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.propios.MisFocosLocalStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoRddMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.propios.MisFocosDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.FocoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.ListadoFocosDashboardDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.ListadoFocosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos.MisFocosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.*
import org.koin.core.module.Module
import org.koin.dsl.module

internal val focosModule by lazy {
    listByElementsOf<Module>(
        focosDataModule,
        listadoFocosDashboardModule,
        listadoFocosModule,
        misFocosModule)
}


private val focosDataModule = module {
    factory { FocosMapper() }
    factory { FocoMapper() }
    factory { FocoDBStore(focoMapper = get()) }
    factory<FocoRepository> { FocoDataRepository(focoDBStore = get()) }
}

private val listadoFocosDashboardModule = module {
    factory { FocosDbDataStore() }
    factory<FocosHabilidadesEnDashboardRepository> {
        ListadoFocosDashboardDataRepository(
            focosStore = get(),
            habilidadesStore = get(),
            seccionesStore = get(),
            zonasStore = get(),
            regionesStore = get(),
            seccionMapper = get(),
            zonaMapper = get(),
            regionMapper = get())
    }
}

private val listadoFocosModule = module {
    factory { FocosCloudDataStore(get(), get()) }
    factory { FocoRddMapper() }
    factory { ListadoGrDBDataStore(grMapper = get()) }
    factory { ListadoGzDBDataStore(gzMapper = get()) }
    factory { ListadoSeDBDataStore(sociaMapper = get()) }
    factory { FocosGrDBDataStore(focoRddMapper = get()) }
    factory { FocosGzDBDataStore(focoRddMapper = get()) }
    factory { FocosSeDBDataStore(focoRddMapper = get()) }
    factory { ListadoFocosDBDataStore(focoRddMapper = get(), focosSeStore = get()) }
    factory<ListadoFocosEnAsignacionRepository> {
        ListadoFocosDataRepository(
            listadoFocosDBDataStore = get(),
            focosSeStore = get(),
            focosGzStore = get(),
            focosGrStore = get(),
            seStore = get(),
            gzStore = get(),
            grStore = get(),
            focosCloudDataStore = get(),
            mapper = get())
    }
}

private val misFocosModule = module {
    factory { MisFocosLocalStore() }
    factory { MisFocosDataMapper() }
    factory<MisFocosRepository> { MisFocosDataRepository(mapper = get(), localStore = get()) }

    factory<GuardadoFocosRepository> { GuardadoFocosDBDataStore(focoRddMapper = get()) }
    factory<GuardadoFocosAsyncRepository> { GuardadoFocosCloudDataStore(get(), get()) }
}
