package biz.belcorp.salesforce.core.data.repository.auth.di

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import org.koin.dsl.module


internal val apiHelperModule = module {

    single { SafeApiCallHelper(get()) }

}
