package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.concursos.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.cloud.ConcursosDBCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.data.ConcursosDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.concursos.ConcursosEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.concursos.ConcursosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos.ConcursosRepository
import org.koin.dsl.module

internal val concursosModule = module {

    factory { ConcursosDBDataStore() }
    factory { ConcursosDBCloudStore(get(), get()) }
    factory { ConcursosEntityDataMapper() }

    factory<ConcursosRepository> {
        ConcursosDataRepository(
            concursosDBDataStore = get(),
            concursosDBCloudStore = get(),
            concursosMapper = get()
        )
    }
}
