package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoSEApiModel

class FocosCloudDataStore(
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun subir(focos: List<DetalleFocoSEApiModel>) = apiCallHelper.safeSingleApiCall {
        syncRestApi.focosSE(focos)
    }

}
