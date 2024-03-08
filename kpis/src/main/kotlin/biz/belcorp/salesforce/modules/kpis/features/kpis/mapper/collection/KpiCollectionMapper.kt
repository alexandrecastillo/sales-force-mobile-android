package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.collection

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCollectionModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

class KpiCollectionMapper(private val textResolver: CollectionTextResolver) {

    fun map(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when {
            role.isMultiProfile() -> {
                val header = getHeader(isMultiProfile = true)
                createKpiCollection(this, header)
            }
            role.isSE() -> {
                val header = getHeader(isMultiProfile = false)
                createKpiCollection(this, header)
            }
            else -> KpiModel()
        }
    }

    private fun getHeader(isMultiProfile: Boolean): String {
        return if (!isMultiProfile) textResolver.formatHeader()
        else textResolver.formatHeaderMultiProfile()
    }

    private fun createKpiCollection(kpiContainer: KpiContainer, header: String): KpiModel =
        with(kpiContainer) {
            val previous = collections.previousData
                ?: CollectionIndicator(campaign = collections.previousCampaign)
            return KpiCollectionModel(
                code = NUMBER_THREE,
                iconRes = R.drawable.ic_kpis_collection,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorCollection,
                header = header,
                type = KpiViewType.KPI_TYPE_COLLECTION,
                order = NUMBER_THREE,
                isThirdPerson = isThirdPerson,
                title = textResolver.formatTitle(
                    previous.campaign.takeLastTwo(),
                    isThirdPerson = isThirdPerson
                ),
                description = textResolver.formatDescriptionRecovery(previous.percentage.toPercentageFormat()),
                descriptionSecond = textResolver.formatDescriptionChargedOrders(
                    previous.ordersTotalCollected.formatWithThousands(),
                    previous.ordersTotal.formatWithThousands()
                )
            )
        }

}
