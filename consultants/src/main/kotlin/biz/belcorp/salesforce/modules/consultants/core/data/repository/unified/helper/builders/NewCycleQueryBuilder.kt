package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.`in`
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.notEqual
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.NewCycleFilter
import io.objectbox.query.QueryBuilder
import java.util.*

class NewCycleQueryBuilder : ConsultantQueryBuilder<NewCycleFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: NewCycleFilter
    ): QueryBuilder<ConsultantEntity> {
        val new6d6 = FilterKey.KEY_6D6.toLowerCase(Locale.getDefault())
        val constancies = createConstanciesFilter(filterable)
        return builder
            .and().equal(ConsultantEntity_.isNew, true)
            .and().equal(ConsultantEntity_.isNewInconstant, false)
            .and().notEqual(ConsultantEntity_.constancyNew, new6d6)
            .doIf(constancies.isNotEmpty()) {
                and().`in`(ConsultantEntity_.constancyNew, constancies)
            }
    }

    private fun createConstanciesFilter(filterable: NewCycleFilter): Array<String> {
        val constancies = mutableListOf<String>()
        with(filterable) {
            if (new1d1) constancies.add(FilterKey.KEY_1D1)
            if (new2d2) constancies.add(FilterKey.KEY_2D2)
            if (new3d3) constancies.add(FilterKey.KEY_3D3)
            if (new4d4) constancies.add(FilterKey.KEY_4D4)
            if (new5d5) constancies.add(FilterKey.KEY_5D5)
        }
        return constancies.map { it.toLowerCase(Locale.getDefault()) }.toTypedArray()
    }

}
