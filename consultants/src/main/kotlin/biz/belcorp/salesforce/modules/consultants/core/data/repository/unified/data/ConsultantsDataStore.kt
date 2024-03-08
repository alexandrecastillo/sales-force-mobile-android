package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.data

import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.core.entities.ConsultantEntity_
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.doIf
import biz.belcorp.salesforce.core.utils.equal
import biz.belcorp.salesforce.core.utils.isCodeEmptyOrNull
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.QueryBuilderHelper
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filter
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filterable
import io.objectbox.kotlin.boxFor
import io.objectbox.query.QueryBuilder

class ConsultantsDataStore(
    private val queryBuilderHelper: QueryBuilderHelper
) {

    fun getConsultants(filter: Filter): List<ConsultantEntity> {
        return boxStore.boxFor<ConsultantEntity>().query()
            .applyUaFilters(filter.uaKey)
            .applyExtraFilters(filter.filterables)
            .build()
            .find()
    }

    private fun QueryBuilder<ConsultantEntity>.applyUaFilters(uaKey: LlaveUA) =
        apply {
            doIf(!uaKey.codigoRegion.isCodeEmptyOrNull()) {
                equal(ConsultantEntity_.region, uaKey.codigoRegion.orEmpty().deleteHyphen())
            }
            doIf(!uaKey.codigoZona.isCodeEmptyOrNull()) {
                and().equal(ConsultantEntity_.zone, uaKey.codigoZona.orEmpty().deleteHyphen())
            }
            doIf(!uaKey.codigoSeccion.isCodeEmptyOrNull()) {
                and().equal(ConsultantEntity_.section, uaKey.codigoSeccion.orEmpty().deleteHyphen())
            }
        }

    private fun QueryBuilder<ConsultantEntity>.applyExtraFilters(filterables: List<Filterable>) =
        apply { queryBuilderHelper.create(this, filterables) }

}
