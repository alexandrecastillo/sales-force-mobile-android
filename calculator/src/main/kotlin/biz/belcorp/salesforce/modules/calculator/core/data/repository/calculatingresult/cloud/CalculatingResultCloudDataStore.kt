package biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.cloud

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity
import biz.belcorp.salesforce.modules.calculator.core.data.network.CalculatorApi
import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.dto.CalculatingResultResponse

class CalculatingResultCloudDataStore(
    private val calculatorApi: CalculatorApi,
    private val apiCallHelper: SafeApiCallHelper
) {

    suspend fun sendResult(entidades: CalculatingResultEntity): CalculatingResultResponse {
        val response = apiCallHelper.safeLegacyApiCall { calculatorApi.saveCalculatingResult(entidades) }
        return requireNotNull(response)
    }
}
