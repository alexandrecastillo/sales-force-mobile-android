package biz.belcorp.salesforce.modules.developmentpath.includes.di

import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.developmentpath.includes.DevelopmentPathModuleIncludes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val includeModules = module {

    factory<ModuleIncludes>(named<DevelopmentPathModuleIncludes>()) { DevelopmentPathModuleIncludes() }

}
