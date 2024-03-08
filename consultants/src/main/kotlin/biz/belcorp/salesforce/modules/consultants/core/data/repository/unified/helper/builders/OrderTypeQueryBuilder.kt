package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.OrderTypeFilter
import io.objectbox.query.QueryBuilder

class OrderTypeQueryBuilder : ConsultantQueryBuilder<OrderTypeFilter>{
    //4/7/2023:  Filter: BAJO_PLUS --> PBVP  not applied by requirement of missing data
    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: OrderTypeFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.highPlusValue){
                and().equal(ConsultantEntity_.orderRange, FilterKey.KEY_HIGH_VALUE_PLUS)
            }
            .doIf(filterable.highValue){
                and().equal(ConsultantEntity_.orderRange, FilterKey.KEY_HIGH_VALUE)
            }
            .doIf(filterable.lowValue){
                and().equal(ConsultantEntity_.orderRange, FilterKey.KEY_LOW_VALUE)
            }
    }
}
