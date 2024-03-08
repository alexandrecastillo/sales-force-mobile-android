package biz.belcorp.salesforce.modules.creditinquiry.core.data.repository

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.creditinquiry.core.data.network.ConsultaCrediticiaRestApi
import io.reactivex.Single


class ConsultaCrediticiaCloudDataStore constructor(
    private val consultaCrediticiaApi: ConsultaCrediticiaRestApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    fun validarRegionZona(region: String, zona: String): Single<Int> {
        return apiCallHelper.safeSingleApiCall {
            consultaCrediticiaApi.validarRegionZona(region, zona)
        }.map { it.resultado?.response ?: Constant.NUMBER_ZERO }
    }

}
