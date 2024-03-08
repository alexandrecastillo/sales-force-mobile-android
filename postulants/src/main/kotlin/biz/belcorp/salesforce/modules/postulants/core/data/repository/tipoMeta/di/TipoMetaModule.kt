package biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta.TipoMetaDBDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta.TipoMetaDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta.TipoMetaEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.TipoMetaRepository

import org.koin.dsl.module

internal val tipoMetaModule = module {

    factory { TipoMetaEntityDataMapper() }
    factory { TipoMetaDBDataStore() }
    factory<TipoMetaRepository> { TipoMetaDataRepository(get(), get()) }
}
