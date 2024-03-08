package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.newcycle

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.core.utils.isMultiProfile
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiNewCyclesModel

class KpiNewCycleMapper(private val textResolver: NewCycleTextResolver) {

    fun map(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when {
            role.isSE() || role.isMultiProfile() -> createKpiNewCycle(this)
            else -> KpiModel()
        }
    }

    private fun createKpiNewCycle(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (isSale) createKpiNewCycleSale(this)
        else createKpiNewCycleBilling(this)
    }

    private fun createKpiNewCycleSale(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val previous =
                newCycles.previousData ?: NewCycleIndicator(campaign = newCycles.previousCampaign)
            val actual =
                newCycles.currentData ?: NewCycleIndicator(campaign = newCycles.currentCampaign)
            return KpiNewCyclesModel(
                code = NUMBER_ZERO,
                iconRes = R.drawable.ic_kpis_cycle_new_consultants,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorNewCycle,
                header = textResolver.formatNewCycleHeader(),
                type = KpiViewType.KPI_TYPE_NEW_CYCLES,
                order = NUMBER_ZERO,
                isThirdPerson = isThirdPerson,
                title = textResolver.formatSaleTitle(
                    actual.campaign.takeLastTwo(),
                    isThirdPerson = isThirdPerson
                ),
                description = textResolver.formatConsultants(
                    previous.lowValueOrders5d5.formatWithThousands(),
                    quantity = previous.lowValueOrders5d5
                ),
                descriptionSecond = textResolver.formatConsultantsHighValue(
                    previous.highValueOrders5d5.formatWithThousands(),
                    quantity = previous.highValueOrders5d5
                )
            )
        }

    private fun createKpiNewCycleBilling(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val previous =
                newCycles.previousData ?: NewCycleIndicator(campaign = newCycles.previousCampaign)
            val actual =
                newCycles.currentData ?: NewCycleIndicator(campaign = newCycles.currentCampaign)
            return KpiNewCyclesModel(
                code = NUMBER_ZERO,
                iconRes = R.drawable.ic_kpis_cycle_new_consultants,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorNewCycle,
                header = textResolver.formatNewCycleHeader(),
                type = KpiViewType.KPI_TYPE_NEW_CYCLES,
                order = NUMBER_ZERO,
                isThirdPerson = isThirdPerson,
                title = textResolver.formatBillingTitle(isThirdPerson = isThirdPerson),
                description = textResolver.formatConsultantsBilling(
                    actual.lowValueOrders6d6.formatWithThousands(),
                    previous.lowValueOrders5d5.formatWithThousands(),
                    quantity = previous.lowValueOrders5d5
                ),
                descriptionSecond = textResolver.formatConsultantsHighValueBilling(
                    actual.highValueOrders6d6.formatWithThousands(),
                    previous.highValueOrders5d5.formatWithThousands(),
                    quantity = previous.lowValueOrders5d5
                )
            )
        }
}
