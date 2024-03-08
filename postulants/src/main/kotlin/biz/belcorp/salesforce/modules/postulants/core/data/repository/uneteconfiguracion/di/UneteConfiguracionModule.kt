package biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion.ConfiguracionDBStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion.ConfiguracionEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.uneteconfiguracion.UneteConfiguracionDataRepository
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.UneteConfiguracionRepository
import org.koin.dsl.module

internal val uneteConfiguracionModule = module {

    factory { ConfiguracionEntityDataMapper() }

    factory { ConfiguracionDBStore() }

    factory { UneteConfiguracionDataRepository(get(), get()) as UneteConfiguracionRepository }
}
