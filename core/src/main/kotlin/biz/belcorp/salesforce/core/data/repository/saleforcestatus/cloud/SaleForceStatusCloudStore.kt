package biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud

import biz.belcorp.salesforce.core.data.network.BelcorpApi
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.dto.SalesForceStatusDto

class SaleForceStatusCloudStore(
    private val belcorpApi: BelcorpApi
) {

    suspend fun getSaleForceStatus(
        countryCode: String,
        region: String,
        zone: String,
        section: String,
        campaigns: String
    ): SalesForceStatusDto? {

        val response = belcorpApi.getReportSalesForceStatus(
            countryCode,
            region,
            zone,
            section,
            "SE",
            campaigns
        )

        return response.body()?.first()
    }
}
