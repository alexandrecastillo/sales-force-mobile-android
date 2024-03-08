package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders

import biz.belcorp.salesforce.core.utils.isMultiProfile
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

class KpiSaleOrdersMapper(private val textResolver: SaleOrdersTextResolver) {

    private val kpiProfileSEMapper by lazy { KpiSaleOrdersSEMapper(textResolver) }
    private val kpiMultiProfileMapper by lazy { KpiSaleOrdersMultiProfileMapper(textResolver) }

    fun map(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when {
            role.isSE() -> kpiProfileSEMapper.createKpiSaleOrders(this)
            role.isMultiProfile() -> kpiMultiProfileMapper.createKpiSaleOrders(this)
            else -> KpiModel()
        }
    }
}
