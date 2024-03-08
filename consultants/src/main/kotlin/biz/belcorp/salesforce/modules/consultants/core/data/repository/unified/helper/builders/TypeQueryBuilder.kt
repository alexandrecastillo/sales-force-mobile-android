package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.TypeFilter
import io.objectbox.query.QueryBuilder

class TypeQueryBuilder : ConsultantQueryBuilder<TypeFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: TypeFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.hasCashPayment && !filterable.hasNotCashPayment) {
                and().equal(ConsultantEntity_.hasCashPayment, true)
            }
            .doIf(filterable.hasNotCashPayment && !filterable.hasCashPayment) {
                and().equal(ConsultantEntity_.hasCashPayment, false)
            }
    }

}
