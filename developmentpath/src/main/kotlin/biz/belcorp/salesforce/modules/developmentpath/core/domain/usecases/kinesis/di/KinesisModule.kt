package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase
import org.koin.dsl.module

internal val kinesisModule = module {
    factory { KinesisManagerUseCase(get(), get(), get(), get(), get()) }
}
