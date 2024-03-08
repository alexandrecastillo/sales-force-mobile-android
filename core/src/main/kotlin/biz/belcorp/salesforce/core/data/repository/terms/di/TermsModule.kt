package biz.belcorp.salesforce.core.data.repository.terms.di

import biz.belcorp.salesforce.core.data.repository.terms.TermsConditionsDataRepository
import biz.belcorp.salesforce.core.data.repository.terms.TermsConditionsMapper
import biz.belcorp.salesforce.core.data.repository.terms.cloud.TermsConditionsCloudStore
import biz.belcorp.salesforce.core.data.repository.terms.data.TermsConditionsDataStore
import biz.belcorp.salesforce.core.domain.repository.terms.TermsConditionsRepository
import org.koin.dsl.module

val termsConditionsModule = module {

    factory { TermsConditionsMapper() }
    factory { TermsConditionsDataStore() }
    factory { TermsConditionsCloudStore(get(), get()) }

    factory<TermsConditionsRepository> { TermsConditionsDataRepository(get(), get(), get()) }

}
