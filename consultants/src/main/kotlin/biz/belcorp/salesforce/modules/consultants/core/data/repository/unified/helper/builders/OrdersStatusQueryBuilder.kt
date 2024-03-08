package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.OrdersStatusFilter
import io.objectbox.query.QueryBuilder

class OrdersStatusQueryBuilder : ConsultantQueryBuilder<OrdersStatusFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: OrdersStatusFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.withDebt) {
                and().greater(ConsultantEntity_.pendingDebt, Constant.NUMBER_ZERO.toLong())
            }
            .doIf(filterable.notMinimumAmount) {
                and().less(ConsultantEntity_.orderAmount, filterable.params.minimalOrderAmount)
            }
            .doIf(filterable.nearToHighValueOrder) {
                and().greater(ConsultantEntity_.orderAmount, filterable.params.tippingPoint)
            }
    }

}
