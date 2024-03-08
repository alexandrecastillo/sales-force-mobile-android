package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.notIn
import biz.belcorp.salesforce.core.constants.ConsultantState
import biz.belcorp.salesforce.core.constants.OrderStatusSic
import biz.belcorp.salesforce.core.constants.OrderStatusSic.FACTURADO
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.BillingFilter
import io.objectbox.kotlin.equal
import io.objectbox.query.QueryBuilder

class BillingQueryBuilder : ConsultantQueryBuilder<BillingFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: BillingFilter
    ): QueryBuilder<ConsultantEntity> {
        val inactives = ConsultantState.inactivesFilter
        val notBilledOrders = OrderStatusSic.notBilledOrdersFilter.toIntArray()
        return builder
            .notIn(ConsultantEntity_.stateCode, inactives)
            .doIf(filterable.hasNotBilledOrders) {
                and().`in`(ConsultantEntity_.billingOrderStatus, notBilledOrders)
            }
            .doIf(filterable.hasBilledOrders) {
                and().equal(ConsultantEntity_.billingOrderStatus, FACTURADO)
            }
    }

}
