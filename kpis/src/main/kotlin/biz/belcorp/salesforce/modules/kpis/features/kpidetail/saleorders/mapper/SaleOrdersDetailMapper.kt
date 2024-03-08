package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersDetail

class SaleOrdersDetailMapper(private val textResolver: SaleOrdersDetailTextResolver) {

    fun map(entity: SaleOrdersDetail): String = with(entity) {
        val (role, campaign) = when {
            role.isDV() -> Pair(Rol.GERENTE_REGION.nameAsCode(), campaignCode.takeLastTwo())
            role.isGR() -> Pair(Rol.GERENTE_ZONA.nameAsCode(), campaignCode.takeLastTwo())
            role.isGZ() -> Pair(Rol.SOCIA_EMPRESARIA.nameAsCode(), campaignCode.takeLastTwo())
            else -> Pair(EMPTY_STRING, EMPTY_STRING)
        }
        return textResolver.formatTitle(role.toLowerCase(), campaign)
    }
}