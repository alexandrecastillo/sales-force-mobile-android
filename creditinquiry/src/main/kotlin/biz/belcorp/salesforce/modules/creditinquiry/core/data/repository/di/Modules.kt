package biz.belcorp.salesforce.modules.creditinquiry.core.data.repository.di

import biz.belcorp.salesforce.modules.creditinquiry.core.data.repository.ConsultaCrediticiaCloudDataStore
import biz.belcorp.salesforce.modules.creditinquiry.core.data.repository.ConsultaCrediticiaDataRepository
import biz.belcorp.salesforce.modules.creditinquiry.core.data.repository.ValidacionCrediticiaCloudDataStore
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.repository.ConsultaCrediticiaRepository
import org.koin.dsl.module

val repositoryModules = module {

    factory { ConsultaCrediticiaCloudDataStore(get(), get()) }
    factory { ValidacionCrediticiaCloudDataStore(get()) }

    factory {
        ConsultaCrediticiaDataRepository(
            get(),
            get(),
            get(),
            get()
        ) as ConsultaCrediticiaRepository
    }

}
