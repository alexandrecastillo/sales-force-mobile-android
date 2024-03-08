package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders

import biz.belcorp.salesforce.core.constants.ConsultantState
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.entities.digital.DigitalConsultantEntity_
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.notIn
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.DigitalFilter
import io.objectbox.query.QueryBuilder

class DigitalQueryBuilder : ConsultantQueryBuilder<DigitalFilter> {

    override fun create(
        builder: QueryBuilder<ConsultantEntity>,
        filterable: DigitalFilter
    ): QueryBuilder<ConsultantEntity> {
        val inactives = ConsultantState.inactivesFilter
        return builder
            .notIn(ConsultantEntity_.stateCode, inactives)
            .doIf(filterable.isSubscribed) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.isSubscribed, true)
            }
            .doIf(filterable.isNotSubscribed) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.isSubscribed, false)
            }
            .doIf(filterable.share) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.share, true)
            }
            .doIf(filterable.noShare) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.share, false)
            }
            .doIf(filterable.buy) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.buy, true)
            }
            .doIf(filterable.noBuy) {
                link(ConsultantEntity_.digitalInfo).equal(DigitalConsultantEntity_.buy, false)
            }
    }

}
