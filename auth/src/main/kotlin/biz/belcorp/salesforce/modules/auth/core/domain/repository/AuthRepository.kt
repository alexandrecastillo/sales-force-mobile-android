package biz.belcorp.salesforce.modules.auth.core.domain.repository

import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase


interface AuthRepository {

    suspend fun login(request: LoginUseCase.Params)

    fun validateCredentials(request: LoginUseCase.Params)

    fun logout()

}
