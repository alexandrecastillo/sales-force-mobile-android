package biz.belcorp.salesforce.core.include.di

import biz.belcorp.salesforce.core.include.IncludeManager
import org.koin.dsl.module


val includeModule = module {

    factory { IncludeManager(getAll()) }

}
