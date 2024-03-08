package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.ConsultantState
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.`in`
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.StateFilter
import io.objectbox.query.QueryBuilder

class StateQueryBuilder : ConsultantQueryBuilder<StateFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: StateFilter
    ): QueryBuilder<ConsultantEntity> {
        val states = createStateFilter(filterable)
        return builder
            .doIf(states.isNotEmpty()) {
                and().`in`(ConsultantEntity_.stateCode, states)
            }
    }

    private fun createStateFilter(filterable: StateFilter): Array<String> {
        val states = mutableListOf<String>()
        with(filterable) {
            if (isEntries) states.add(ConsultantState.INGRESO)
            if (isReentries) states.add(ConsultantState.REINGRESO)
        }
        return states.toTypedArray()
    }

}
