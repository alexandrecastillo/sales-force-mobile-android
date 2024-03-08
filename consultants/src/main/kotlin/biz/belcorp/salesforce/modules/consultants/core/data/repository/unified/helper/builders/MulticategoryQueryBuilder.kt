package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.MulticategoryFilter
import io.objectbox.query.QueryBuilder

class MulticategoryQueryBuilder : ConsultantQueryBuilder<MulticategoryFilter>{
    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: MulticategoryFilter
    ): QueryBuilder<ConsultantEntity> {


        return builder
            .doIf(filterable.yesMulticategory && !filterable.noMulticategory){
                and().equal(ConsultantEntity_.multicategory, true)
            }
            .doIf(filterable.noMulticategory && !filterable.yesMulticategory){
                and().equal(ConsultantEntity_.multicategory, false)
            }

    }

}
