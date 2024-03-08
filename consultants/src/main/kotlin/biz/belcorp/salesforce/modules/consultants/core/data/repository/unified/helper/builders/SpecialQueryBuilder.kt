package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.notEqual
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.SpecialFilter
import io.objectbox.query.QueryBuilder

class SpecialQueryBuilder : ConsultantQueryBuilder<SpecialFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: SpecialFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.skipBrilliants) {
                and().notEqual(ConsultantEntity_.brightSegmentCode, BRIGHT_SEGMENT_BRILLIANT_CODE)
            }
    }

    companion object {

        private const val BRIGHT_SEGMENT_BRILLIANT_CODE = "6"

    }

}
