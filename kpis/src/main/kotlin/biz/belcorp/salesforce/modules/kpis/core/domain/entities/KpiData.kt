package biz.belcorp.salesforce.modules.kpis.core.domain.entities

import biz.belcorp.salesforce.core.constants.Constant

class KpiData<out T>(
    private val data: Map<String, List<T>>,
    val currentCampaign: String = Constant.EMPTY_STRING,
    val previousCampaign: String = Constant.EMPTY_STRING
) {

    val currentData by lazy { data[currentCampaign]?.firstOrNull() }

    val previousData by lazy { data[previousCampaign]?.firstOrNull() }

    val isDataAvailable by lazy { currentData != null || previousData != null }

}
