package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.`in`
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.OrdersFilter
import io.objectbox.query.QueryBuilder

class OrdersQueryBuilder : ConsultantQueryBuilder<OrdersFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: OrdersFilter
    ): QueryBuilder<ConsultantEntity> {
        val ranges = createRangeFilters(filterable)
        return builder
            .and().equal(ConsultantEntity_.isOrderSent, true)
            .doIf(filterable.withDebt) {
                and().greater(ConsultantEntity_.pendingDebt, Constant.NUMBER_ZERO.toLong())
            }
            .doIf(ranges.isNotEmpty()) {
                and().`in`(ConsultantEntity_.orderRange, ranges)
            }
    }

    private fun createRangeFilters(filterable: OrdersFilter): Array<String> {
        val ranges = mutableListOf<String>()
        with(filterable) {
            if (lowValueOrder) ranges.add(FilterKey.KEY_LOW_VALUE)
            if (lowValueOrderPlus) ranges.add(FilterKey.KEY_LOW_VALUE_PLUS)
            if (highValueOrder) ranges.add(FilterKey.KEY_HIGH_VALUE)
            if (highValueOrderPlus) ranges.add(FilterKey.KEY_HIGH_VALUE_PLUS)
        }
        return ranges.toTypedArray()
    }

}
