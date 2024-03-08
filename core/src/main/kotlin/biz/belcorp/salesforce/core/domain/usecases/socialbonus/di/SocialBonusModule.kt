package biz.belcorp.salesforce.core.domain.usecases.socialbonus.di

import biz.belcorp.salesforce.core.domain.usecases.socialbonus.SocialBonusUseCase
import org.koin.dsl.module

internal val socialBonusModule = module {
    factory { SocialBonusUseCase(get(), get(), get()) }
}
