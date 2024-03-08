package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.cloud.AnotacionCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud.MetaConsultoraCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud.MetaSociaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data.MetaConsultoraDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data.TipoMetaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaConsultoraMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaSociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.TipoMetaEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.MetaConsultoraDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.MetaPersonalDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.MetasSociaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas.TipoMetaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.MetasSociaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.TipoMetaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaConsultoraRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaPersonalRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val metasModule by lazy {
    listByElementsOf<Module>(
        metasGeneralModule,
        anotacionesModule,
        tipoMetaModule
    )
}

private val metasGeneralModule = module {
    factory { MetaConsultoraCloudDataStore(api = get(), apiCallHelper = get()) }
    factory { MetaConsultoraDBDataStore() }
    factory { MetaConsultoraMapper() }

    factory { MetaSociaCloudDataStore(api = get(), apiCallHelper = get()) }
    factory { MetaSociaMapper() }

    factory<MetaConsultoraRepository> {
        MetaConsultoraDataRepository(
            cloudStore = get(),
            dbStore = get(),
            metaConsultoraMapper = get()
        )
    }

    factory { MetaDataMapper() }

    factory<MetaPersonalRepository> {
        MetaPersonalDataRepository(storeDB = get(), storeCloud = get(), mapper = get())
    }

    factory<MetasSociaRepository> {
        MetasSociaDataRepository(cloudStore = get(), dbStore = get(), mapper = get())
    }
}

private val anotacionesModule = module {
    factory { AnotacionDBStore() }
    factory { AnotacionCloudStore(get(), get()) }
}

private val tipoMetaModule = module {
    factory { TipoMetaEntityDataMapper() }
    factory { TipoMetaDBDataStore() }

    factory<TipoMetaRepository> {
        TipoMetaDataRepository(dataStore = get(), dataMapper = get())
    }
}
