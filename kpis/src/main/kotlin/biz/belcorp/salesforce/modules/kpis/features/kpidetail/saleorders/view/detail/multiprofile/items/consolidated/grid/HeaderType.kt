package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid

sealed class HeaderType(val title: String)

class UaHeader(title: String) : HeaderType(title)

sealed class SaleHeaderType(title: String) : HeaderType(title) {
    class SaleReal(title: String) : SaleHeaderType(title)
    class SaleGoal(title: String) : SaleHeaderType(title)
    class SaleFulfillment(title: String) : SaleHeaderType(title)
    class SaleAverage(title: String) : SaleHeaderType(title)
}

sealed class OrderHeaderType(title: String) : HeaderType(title) {
    class OrderLowValue(title: String) : OrderHeaderType(title)
    class OrderHighValue(title: String) : OrderHeaderType(title)
    class OrderHighPlusValue(title: String) : OrderHeaderType(title)
    class OrderReal(title: String) : OrderHeaderType(title)
    class OrderGoal(title: String) : OrderHeaderType(title)
    class OrderFulfillment(title: String) : OrderHeaderType(title)
}

sealed class ActivityHeaderType(title: String) : HeaderType(title) {
    class ActivityReal(title: String) : ActivityHeaderType(title)
    class ActivityGoal(title: String) : ActivityHeaderType(title)
    class ActivityFulfillment(title: String) : ActivityHeaderType(title)
}

sealed class ActivesHeaderType(title: String) : HeaderType(title) {
    class ActivesFinal(title: String) : ActivesHeaderType(title)
    class ActivesLastYear(title: String) : ActivesHeaderType(title)
    class ActivesRetention(title: String) : ActivesHeaderType(title)
    class ActivesGoal(title: String) : ActivesHeaderType(title)
    class ActivesFulfillment(title: String) : ActivesHeaderType(title)
}
