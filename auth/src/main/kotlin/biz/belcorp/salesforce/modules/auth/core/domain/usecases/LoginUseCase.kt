package biz.belcorp.salesforce.modules.auth.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseCrashlyticsRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.auth.core.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val crashlyticsRepository: FirebaseCrashlyticsRepository
) {

    suspend fun login(params: Params): LoginType {
        return try {
            authRepository.login(params)
            crashlyticsRepository.setUserKeys(false)
            sessionRepository.syncProfileData()
            LoginType.ONLINE
        } catch (e: Exception) {
            if (e is NetworkConnectionException) {
                authRepository.validateCredentials(params)
                LoginType.OFFLINE
            } else {
                throw e
            }
        }
    }

    fun loginSuccessful() {
        sessionRepository.setSessionState(hasError = false)
    }

    fun loginFailure() {
        sessionRepository.setSessionState(hasError = true)
    }

    class Params(
        val country: String,
        val ua: LlaveUA? = null,
        val user: String,
        var password: String,
        val appId: String
    )

}
