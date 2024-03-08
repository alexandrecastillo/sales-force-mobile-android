package biz.belcorp.salesforce.core.domain.usecases.saleforcestatus

import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.NoSaleForceStatusDataException
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.Logger

class GetSaleForceStatusUseCase(
    private val sessionRepository: SessionRepository,
    private val userPreferences: UserSharedPreferences,
    private val campaignRepository: CampaniasRepository
) {


    suspend fun saveSaleForceDataInPref(section: String? = null) {
        val previousCampaign = campaignRepository.obtenerCampaniaInmediataAnterior(getAdministrativeUnit())
        sessionRepository.updateSaleForceData(previousCampaign.codigo, section)
    }

    private fun getAdministrativeUnit(): LlaveUA {

        val section = userPreferences.seccion?.ifBlank { null }
        val zone = userPreferences.zona?.ifBlank { null }
        val region = userPreferences.region?.ifBlank { null }

        return LlaveUA(
            codigoRegion = region,
            codigoZona = zone,
            codigoSeccion = section,
            consultoraId = -1L
        )
    }


    /**
     * In order to use the obtener() method, is mandatory to call  saveSaleForceDataInPref()
     * before, the previous method create the request to API and saved in SharedPreferences
     */

    fun obtener(): SaleForceStatus {
        return sessionRepository.getSaleForceStatus() ?: run {
            with(NoSaleForceStatusDataException()) {
                Logger.e(this)
                throw this
            }
        }
    }
}
