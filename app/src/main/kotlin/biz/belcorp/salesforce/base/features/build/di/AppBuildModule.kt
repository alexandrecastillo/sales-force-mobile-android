package biz.belcorp.salesforce.base.features.build.di

import biz.belcorp.salesforce.base.features.build.AppBuildImpl
import biz.belcorp.salesforce.core.utils.AppBuild
import org.koin.dsl.module


val appBuildModule = module {
    single { provideAppBuild() }
}

private fun provideAppBuild(): AppBuild {
    return AppBuildImpl()
}
