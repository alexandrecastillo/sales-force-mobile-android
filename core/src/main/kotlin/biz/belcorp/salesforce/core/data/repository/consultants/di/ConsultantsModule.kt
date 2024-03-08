package biz.belcorp.salesforce.core.data.repository.consultants.di

import biz.belcorp.salesforce.core.data.repository.consultants.data.ConsultantsDataStore
import org.koin.dsl.module


val consultantsModule = module {

    factory { ConsultantsDataStore() }

}
