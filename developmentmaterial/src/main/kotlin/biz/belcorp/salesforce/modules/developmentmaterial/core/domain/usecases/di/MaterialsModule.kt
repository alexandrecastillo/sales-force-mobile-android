package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.di

import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.GetDocumentsUseCase
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.SyncDocumentsUseCase
import org.koin.dsl.module

val documentsModule = module {

    factory { GetDocumentsUseCase(get()) }

    factory { SyncDocumentsUseCase(get(), get()) }

}
