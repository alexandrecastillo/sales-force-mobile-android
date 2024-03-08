package biz.belcorp.salesforce.base.features.home

import androidx.annotation.VisibleForTesting
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.utils.AppTextResolver
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.SingleMapper
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.differenceInDays
import java.util.*

class HomeMapper(private val textResolver: AppTextResolver) : SingleMapper<Sesion, HomeModel>() {

    override fun map(value: Sesion): HomeModel = with(value) {
        val isBilling = CampaignRules.isBilling(campaign, useFirstDayFlag = false)
        return HomeModel(
            periodDescription = createPeriod(campaign, isBilling),
            campaignName = campaign.nombreCorto.deleteHyphen(),
            role = rol,
            isBilling = isBilling,
            isFirstDayBilling = createIsFirstDayBilling(campaign),
            color = createColor(isBilling)
        ).apply {
            uaKey = value.llaveUA
            showBillingBanner = isBillingByRole(this)
        }
    }

    private fun createPeriod(campaign: Campania, isBilling: Boolean): String {
        val campaignName = campaign.nombreCorto.deleteHyphen()
        val days = calculateDays(Date(), campaign.inicioFacturacion)
        return when (isBilling) {
            true -> textResolver.formatToolbarBillingText(campaignName, days)
            else -> textResolver.formatToolbarSaleText(campaignName)
        }
    }

    private fun createColor(isBilling: Boolean): Int {
        return if (isBilling) R.color.magenta else R.color.colorPrimaryText
    }

    private fun createIsFirstDayBilling(campaign: Campania): Boolean {
        return campaign.esPrimerDiaFacturacion
    }

    private fun calculateDays(date: Date?, compareDate: Date?): Int {
        if (date == null || compareDate == null) return NUMBER_ZERO
        return (date differenceInDays compareDate) + NUMBER_ONE
    }

    @VisibleForTesting
    fun isBillingByRole(model: HomeModel): Boolean = with(model) {
        val isDvOrGR = role in listOf(Rol.GERENTE_REGION, Rol.DIRECTOR_VENTAS)
        return if (!isBilling) false else if (isDvOrGR) !isFirstDayBilling else true
    }

}
