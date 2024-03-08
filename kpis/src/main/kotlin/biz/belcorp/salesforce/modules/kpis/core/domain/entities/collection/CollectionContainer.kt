package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import java.util.*

class CollectionContainer(
    val collectionList: KpiData<CollectionIndicator>,
    val profitList: KpiData<ProfitIndicator>,
    val retentionList: RetentionIndicator,
    val capitalizationList: KpiData<CapitalizationIndicator>,
    val currencySymbol: String,
    val campaign: String,
    val role: Rol,
    val syncDate: Date,
    val isThirdPerson: Boolean
)
