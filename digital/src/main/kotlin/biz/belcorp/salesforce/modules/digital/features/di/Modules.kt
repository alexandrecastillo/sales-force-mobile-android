package biz.belcorp.salesforce.modules.digital.features.di

import biz.belcorp.salesforce.modules.digital.features.digital.di.digitalModule
import biz.belcorp.salesforce.modules.digital.features.sync.di.syncModule
import biz.belcorp.salesforce.modules.digital.features.utils.DigitalTextResolver
import org.koin.dsl.module

val featureModules by lazy {
    listOf(
        utilsModule,
        digitalModule,
        syncModule
    )
}


private val utilsModule = module {

    factory { DigitalTextResolver(get()) }

}
