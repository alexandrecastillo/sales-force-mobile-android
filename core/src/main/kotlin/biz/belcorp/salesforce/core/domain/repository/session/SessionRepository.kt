package biz.belcorp.salesforce.core.domain.repository.session

import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.domain.entities.session.Sesion


interface SessionRepository {

    fun getSession(): Sesion?

    fun getSaleForceStatus(): SaleForceStatus?

    fun esSesionActiva(): Boolean

    fun setSessionState(hasError: Boolean)

    suspend fun syncProfileData()

    fun updateSession()

    fun isAvailable(): Boolean

    suspend fun updateSaleForceData(previousCampaign: String, section: String? = null)

}
