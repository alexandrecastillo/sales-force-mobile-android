package biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones.SeccionDBDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones.SeccionDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones.SeccionEntityDataMapper
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.secciones.SeccionRepository
import org.koin.dsl.module

val seccionesModule = module {

    factory { SeccionEntityDataMapper() }

    factory { SeccionDBDataStore() }

    factory<SeccionRepository> { SeccionDataRepository(get(), get()) }

}
