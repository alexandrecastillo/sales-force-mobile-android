package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrdersHeaderModel

sealed class SaleOrdersHeaderViewState {
    class Success(val data: SaleOrdersHeaderModel) : SaleOrdersHeaderViewState()

    class Failed(val message: String) : SaleOrdersHeaderViewState()
}
