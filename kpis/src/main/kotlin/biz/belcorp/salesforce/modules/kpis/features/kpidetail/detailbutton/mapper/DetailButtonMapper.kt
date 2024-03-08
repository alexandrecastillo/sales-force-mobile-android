package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.mapper

import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonInfoModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonType

class DetailButtonMapper(private val textResolver: DetailButtonTextResolver) {

    private fun getBillingButtonTitle(@KpiType kpiType: Int, role: Rol, campaign: String): String {
        return if (role.isSE()) textResolver.getDefaultTitle(kpiType, campaign)
        else textResolver.getBillingButtonTitle()
    }

    private fun getConsultantButtonTitle(@KpiType kpiType: Int, campaign: String): String {
        return textResolver.getDefaultTitle(kpiType, campaign)
    }

    private fun getDetailButtonInfo(
        campaign: String,
        isBilling: Boolean,
        @KpiType kpiType: Int,
        role: Rol
    ): DetailButtonInfoModel {
        return if (isBilling) createBillingButtonInfo(kpiType, role, campaign)
        else DetailButtonInfoModel(
            title = getConsultantButtonTitle(kpiType, campaign),
            type = DetailButtonType.CONSULTANT
        )
    }

    private fun getBillingButtonInfo(
        campaign: String,
        isBilling: Boolean,
        @KpiType kpiType: Int,
        role: Rol
    ): DetailButtonInfoModel {
        return if (isBilling) createBillingButtonInfo(kpiType, role, campaign)
        else DetailButtonInfoModel(type = DetailButtonType.NONE)
    }

    private fun createBillingButtonInfo(
        @KpiType kpiType: Int,
        role: Rol,
        campaign: String
    ): DetailButtonInfoModel {
        return DetailButtonInfoModel(
            title = getBillingButtonTitle(kpiType, role, campaign),
            type = DetailButtonType.BILLING
        )
    }

    fun map(
        campaign: String,
        isBilling: Boolean,
        @KpiType kpiType: Int,
        role: Rol
    ): DetailButtonInfoModel {
        return when {
            role.isDV() || role.isGR() -> getBillingButtonInfo(campaign,isBilling, kpiType, role)
            role.isGZ() || role.isSE() -> getDetailButtonInfo(campaign, isBilling, kpiType, role)
            else -> DetailButtonInfoModel(type = DetailButtonType.NONE)
        }
    }
}
