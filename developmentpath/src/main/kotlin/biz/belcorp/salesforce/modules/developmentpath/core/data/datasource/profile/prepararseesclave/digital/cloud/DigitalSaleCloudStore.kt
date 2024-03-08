package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.dto.DigitalSaleSeDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncProfileInfoApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleCoQuery
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.model.DigitalSaleSeQuery

class DigitalSaleCloudStore(
    private val api: SyncProfileInfoApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun getDigitalSaleConsultant(query: DigitalSaleCoQuery): DigitalSaleCoDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getDigitalSaleConsultant(request)
        }
        return requireNotNull(response).data
    }

    suspend fun getDigitalSaleBusinessPartner(query: DigitalSaleSeQuery): DigitalSaleSeDto {
        val request = query.get().toRequestString()
        val response = apiCallHelper.safeBelcorpApiCall {
            api.getDigitalSaleBusinessPartner(request)
        }
        return requireNotNull(response).data
    }
}
