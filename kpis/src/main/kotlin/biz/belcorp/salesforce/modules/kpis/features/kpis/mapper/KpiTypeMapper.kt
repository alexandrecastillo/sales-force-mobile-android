package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper

import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType

object KpiTypeMapper {

    fun parse(kpiType: Int): Int {
        return when (kpiType) {
            KpiViewType.KPI_TYPE_COLLECTION -> KpiType.COLLECTION
            KpiViewType.KPI_TYPE_NEW_CYCLES -> KpiType.NEW_CYCLES
            KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING,
            KpiViewType.KPI_TYPE_SALE_ORDERS_SALE,
            KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_SALE,
            KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_BILLING -> KpiType.SALE_ORDERS
            KpiViewType.KPI_TYPE_CAPITALIZATION_BILLING,
            KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_BILLING,
            KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_SALE,
            KpiViewType.KPI_TYPE_CAPITALIZATION_SALE -> KpiType.CAPITALIZATION
            else -> KpiType.NONE
        }
    }
}
