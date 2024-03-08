package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class KpiGridSelectorMapper(private val textResolver: KpiGridSelectorTextResolver) {

    fun getAdvancementLabel(role: Rol, campaign: String): String {
        return if (campaign.isNotEmpty()) textResolver.getAdvancementByUa(
            role,
            campaign
        )
        else EMPTY_STRING
    }

}
