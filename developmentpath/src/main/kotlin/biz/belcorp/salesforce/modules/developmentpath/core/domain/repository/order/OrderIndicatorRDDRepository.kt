package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.order

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.OrderIndicatorRDD

interface OrderIndicatorRDDRepository {
    suspend fun getOrderIndicator(uaKey: LlaveUA, campaign: String): OrderIndicatorRDD
}
