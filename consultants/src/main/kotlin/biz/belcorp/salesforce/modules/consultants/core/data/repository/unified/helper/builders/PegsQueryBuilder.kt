package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.constants.OrderStatusSic
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.PegFilter
import io.objectbox.query.QueryBuilder

class PegsQueryBuilder : ConsultantQueryBuilder<PegFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: PegFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.isPeg) {
                and().equal(ConsultantEntity_.isPeg, true)
            }
            .doIf(filterable.isPossiblePeg && !filterable.isPeg) {
                and().equal(ConsultantEntity_.isPeg, false)
            }
            .doIf(filterable.isPossiblePeg) {
                val notBilledOrders = OrderStatusSic.notBilledOrdersFilter.toIntArray()
                and().`in`(ConsultantEntity_.billingOrderStatus, notBilledOrders)
            }
    }
}
