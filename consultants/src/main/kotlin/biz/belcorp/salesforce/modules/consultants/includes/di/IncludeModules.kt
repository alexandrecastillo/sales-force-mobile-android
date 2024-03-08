package biz.belcorp.salesforce.modules.consultants.includes.di

import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.consultants.includes.ConsultantsModuleIncludes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val includeModules = module {

    factory<ModuleIncludes>(named<ConsultantsModuleIncludes>()) { ConsultantsModuleIncludes() }

}
