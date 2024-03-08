package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.MultibrandFilter
import io.objectbox.query.QueryBuilder

class MultibrandQueryBuilder : ConsultantQueryBuilder<MultibrandFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: MultibrandFilter
    ): QueryBuilder<ConsultantEntity> {
        return builder
            .doIf(filterable.yesMultiBrand && !filterable.noMultiBrand) {
                and().equal(ConsultantEntity_.multiMarca, true)
            }
            .doIf(filterable.noMultiBrand && !filterable.yesMultiBrand) {
                and().equal(ConsultantEntity_.multiMarca, false)
            }
    }

}
