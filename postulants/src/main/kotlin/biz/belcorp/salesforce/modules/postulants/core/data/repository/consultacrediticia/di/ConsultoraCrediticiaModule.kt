package biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.ConsultaCrediticiaDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.datasource.ValidacionCrediticiaCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.mappers.BloqueoEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.mappers.ValidacionCrediticiaEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ConsultaCrediticiaRepository
import org.koin.dsl.module

internal val consultoraCrediticiaModule = module {

    factory { BloqueoEntityDataMapper() }
    factory { ValidacionCrediticiaEntityDataMapper() }

    factory { ValidacionCrediticiaCloudDataStore(get()) }

    factory<ConsultaCrediticiaRepository> {
        ConsultaCrediticiaDataRepository(get(), get())
    }
}
