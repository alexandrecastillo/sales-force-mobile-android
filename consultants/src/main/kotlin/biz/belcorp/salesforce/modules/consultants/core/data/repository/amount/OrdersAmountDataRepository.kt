package biz.belcorp.salesforce.modules.consultants.core.data.repository.amount

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.OrdersAmountCloudStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountDto
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountParams
import biz.belcorp.salesforce.modules.consultants.core.data.repository.amount.cloud.dto.OrderAmountQuery
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.amount.OrdersAmountRepository


class OrdersAmountDataRepository(
    private val ordersAmountCloudStore: OrdersAmountCloudStore,
    private val consultantsDataStore: ConsultantsDataStore
) : OrdersAmountRepository {

    override suspend fun fetch(countryCode: String, uaKey: LlaveUA, campaignCode: String) {

        val params = OrderAmountParams().apply {
            country = countryCode
            region = uaKey.codigoRegion.orEmpty()
            zone = uaKey.codigoZona.orEmpty()
            section = uaKey.codigoSeccion.orEmpty()
            campaign = campaignCode
        }

        val query = OrderAmountQuery(params)
        val amounts = ordersAmountCloudStore.getOrdersAmount(query)

        saveConsultantsAmounts(uaKey, amounts)
    }

    private fun saveConsultantsAmounts(uaKey: LlaveUA, amounts: List<OrderAmountDto.OrderAmount>) {

        val consultants = consultantsDataStore.getConsultants(uaKey)
        val consultantsMap = consultants.map { it.consultantId to it }.toMap()

        amounts.forEach {
            consultantsMap[it.consultantId]?.sbAmount = it.amount
        }

        consultantsDataStore.updateConsultants(consultantsMap.values.toList())
    }

}
