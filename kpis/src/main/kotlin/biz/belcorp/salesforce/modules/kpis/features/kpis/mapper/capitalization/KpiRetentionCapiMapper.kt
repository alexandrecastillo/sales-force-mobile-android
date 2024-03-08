package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.capitalization

import biz.belcorp.salesforce.core.utils.isMultiProfile
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.RetentionCapiTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

class KpiRetentionCapiMapper(private val textResolver: RetentionCapiTextResolver) {

    private val kpiRetentionCapiSeMapper by lazy { KpiCapitalizationSEMapper(textResolver) }
    private val kpiRetentionCapiMultiProfileMapper by lazy {
        KpiCapitalizationMultiProfileMapper(textResolver)
    }

    fun map(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when {
            role.isSE() -> kpiRetentionCapiSeMapper.createKpiCapitalization(this)
            role.isMultiProfile() -> kpiRetentionCapiMultiProfileMapper.createKpiCapitalization(this)
            else -> KpiModel()
        }
    }
}
