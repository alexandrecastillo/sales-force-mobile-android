package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrdersHeaderModel

sealed class MultiBrandViewState {
    class Success(val data: SaleOrdersHeaderModel?) : MultiBrandViewState()

    class SuccessSE(val data: SaleOrdersHeaderModel?) : MultiBrandViewState()

    class Failed(val message: String) : MultiBrandViewState()
}
