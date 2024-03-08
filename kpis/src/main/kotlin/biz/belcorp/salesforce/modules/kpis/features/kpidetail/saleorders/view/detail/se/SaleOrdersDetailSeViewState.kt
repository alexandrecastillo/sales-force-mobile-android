package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrdersDetailModel

sealed class SaleOrdersDetailSeViewState {
    class Success(val model: SaleOrdersDetailModel) : SaleOrdersDetailSeViewState()
    class Failed(val message: String) : SaleOrdersDetailSeViewState()
}
