package biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.di

import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.MaterialesDesarrolloCloudStore
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.MaterialesDesarrolloDataRepository
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.MaterialesDesarrolloDataStore
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.MaterialesDesarrolloMapper
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository
import org.koin.dsl.module

val documentsModule = module {

    factory { MaterialesDesarrolloMapper() }

    factory { MaterialesDesarrolloDataStore() }
    factory { MaterialesDesarrolloCloudStore(get(), get()) }

    factory<MaterialesDesarrolloRepository> { MaterialesDesarrolloDataRepository(get(), get(), get()) }

}
