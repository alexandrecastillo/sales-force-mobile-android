package biz.belcorp.salesforce.modules.digital.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo

interface DigitalRepository {

    suspend fun sync(country: String, campaign: String, uaKey: LlaveUA)

    suspend fun getDigitalInfo(campaign: String, uaKey: LlaveUA): DigitalInfo

    suspend fun getDigitalInfoByParent(campaign: String, uaKey: LlaveUA): List<DigitalInfo>

}
