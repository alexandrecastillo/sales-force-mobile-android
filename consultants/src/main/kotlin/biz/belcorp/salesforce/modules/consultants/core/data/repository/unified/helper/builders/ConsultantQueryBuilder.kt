package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filterable
import io.objectbox.query.QueryBuilder

interface ConsultantQueryBuilder<T : Filterable> {

    fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: T
    ): QueryBuilder<ConsultantEntity>

}
