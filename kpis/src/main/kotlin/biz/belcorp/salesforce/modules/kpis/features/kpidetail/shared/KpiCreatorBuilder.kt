package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import biz.belcorp.salesforce.core.base.FragmentCreator
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiDetailParams
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationFragmentCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.creator.GainFragmentCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.creator.NewCycleFragmentCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.creator.SaleOrdersFragmentCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.utils.Constant.KPI_NOT_TYPE_EXIST

object KpiCreatorBuilder {

    fun build(params: KpiFragmentParameters): FragmentCreator<KpiFragmentParameters> {
        return when (params.kpiType) {
            KpiType.SALE_ORDERS -> getSaleOrdersCreator(params)
            KpiType.COLLECTION -> getGainCreator(params)
            KpiType.NEW_CYCLES -> getNewCycleCreator(params)
            KpiType.CAPITALIZATION -> getRetentionCapsCreator(params)
            else -> throw ClassNotFoundException(KPI_NOT_TYPE_EXIST)
        }
    }

    private fun getSaleOrdersCreator(kpiParams: KpiFragmentParameters) =
        SaleOrdersFragmentCreator.init(kpiParams)

    private fun getNewCycleCreator(kpiParams: KpiFragmentParameters) =
        NewCycleFragmentCreator.init(kpiParams)

    private fun getGainCreator(kpiParams: KpiFragmentParameters) =
        GainFragmentCreator.init(kpiParams)

    private fun getRetentionCapsCreator(kpiParams: KpiFragmentParameters) =
        CapitalizationFragmentCreator.init(kpiParams)
}
