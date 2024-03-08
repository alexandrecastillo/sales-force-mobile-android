package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.concursos.ConcursosUseCaseImpl
import org.koin.dsl.module

internal val concursosModule = module {
    factory { ConcursosUseCaseImpl(get(), get(), get(), get()) }
}
