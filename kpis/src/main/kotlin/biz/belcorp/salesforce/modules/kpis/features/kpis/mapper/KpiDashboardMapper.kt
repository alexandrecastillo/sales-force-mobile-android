package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.campania.Campania.Periodo
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.collection.KpiCollectionMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.newcycle.KpiNewCycleMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.capitalization.KpiRetentionCapiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders.KpiSaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboard
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

class KpiDashboardMapper(
    private val kpiSaleOrdersMapper: KpiSaleOrdersMapper,
    private val kpiCollectionMapper: KpiCollectionMapper,
    private val kpiNewCycleMapper: KpiNewCycleMapper,
    private val kpiRetentionCapiMapper: KpiRetentionCapiMapper,
    private val textResolver: KpiDashboardTextResolver
) {

    fun map(kpiContainer: KpiContainer): KpiDashboard = with(kpiContainer) {
        val kpi = arrayListOf<KpiModel>()
        val saleOrdersData = kpiSaleOrdersMapper.map(this)
        val collectionData = kpiCollectionMapper.map(this)
        val newCycleData = kpiNewCycleMapper.map(this)
        val capitalizationData = kpiRetentionCapiMapper.map(this)

        if (saleOrdersData.isValid) kpi.add(saleOrdersData)
        if (collectionData.isValid) kpi.add(collectionData)
        if (newCycleData.isValid) kpi.add(newCycleData)
        if (capitalizationData.isValid) kpi.add(capitalizationData)

        return KpiDashboard(
            title = mapTitle(campaign),
            role = sessionRole,
            kpis = kpi.sortedBy { it.order })
    }

    private fun mapTitle(campaign: Campania): String = with(campaign) {
        return when (periodo) {
            Periodo.FACTURACION -> textResolver.formatTitleKpisDashboard(campaign.nombreCorto.deleteHyphen())
            else -> Constant.EMPTY_STRING
        }
    }
}
