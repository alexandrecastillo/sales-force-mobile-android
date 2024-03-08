package biz.belcorp.salesforce.core.domain.repository.device

import biz.belcorp.salesforce.core.domain.entities.session.Sesion


interface DeviceRepository {

    fun resetFcmTokenPending()

    suspend fun saveToken(token: String, session: Sesion?)

    suspend fun syncTokenIfNeeded(session: Sesion?)

}
