package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.isPositive
import biz.belcorp.salesforce.core.utils.isZeroOrNegative
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.RetentionFilter
import io.objectbox.query.QueryBuilder

class RetentionQueryBuilder : ConsultantQueryBuilder<RetentionFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: RetentionFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .and()
            .equal(ConsultantEntity_.isPeg, true)
            .doIf(filterable.pegsWithDebt && !filterable.pegsWithoutDebt) {
                and().isPositive(ConsultantEntity_.pendingDebt)
            }
            .doIf(filterable.pegsWithoutDebt && !filterable.pegsWithDebt) {
                and().isZeroOrNegative(ConsultantEntity_.pendingDebt)
            }
    }
}
