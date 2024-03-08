package biz.belcorp.salesforce.modules.auth.core.domain.usecases.di

import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import org.koin.dsl.module
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LogoutUseCase as LogoutUseCaseImpl

val authModule = module {

    factory { LoginUseCase(get(), get(), get()) }
    factory<LogoutUseCase> { LogoutUseCaseImpl(get(), get(), get()) }

}
