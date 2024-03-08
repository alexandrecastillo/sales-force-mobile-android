package biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete.ParametroUneteDBStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete.ParametroUneteDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete.ParametroUneteEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ParametroUneteRepository
import org.koin.dsl.module

internal val parametroUneteModule = module {

    factory { ParametroUneteEntityDataMapper() }

    factory { ParametroUneteDBStore() }

    factory { ParametroUneteDataRepository(get(), get()) as ParametroUneteRepository }
}
