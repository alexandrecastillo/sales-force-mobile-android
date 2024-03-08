package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.ConsultoraDBDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.ConsultoraDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers.ConsultoraEntityDataMapper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultoras.mappers.ConsultoraPosibleCambioNivelEntityDataMapper
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.consultoras.ConsultoraRepository
import org.koin.dsl.module


val consultorasModule = module {

    factory { ConsultoraEntityDataMapper() }
    factory { ConsultoraPosibleCambioNivelEntityDataMapper() }
    factory { ConsultoraDBDataStore() }
    factory<ConsultoraRepository> { ConsultoraDataRepository(get(), get(), get(), get()) }

}
