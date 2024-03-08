package biz.belcorp.salesforce.modules.kpis.includes.di

import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.kpis.includes.KpiModuleIncludes
import org.koin.core.qualifier.named
import org.koin.dsl.module

val includeModules = module {

    factory<ModuleIncludes>(named<KpiModuleIncludes>()) { KpiModuleIncludes() }

}
