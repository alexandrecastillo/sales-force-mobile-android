package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile

sealed class SaleOrdersDetailViewState {
    class Success(val title: String) : SaleOrdersDetailViewState()
    class Failed(val message: String) : SaleOrdersDetailViewState()
}
