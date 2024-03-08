package biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified

class OrdersStatusFilter(
    val withDebt: Boolean = false,
    val notMinimumAmount: Boolean = false,
    val nearToHighValueOrder: Boolean = false,
    val params: Params
) : Filterable {

    class Params(
        val minimalOrderAmount: Long,
        val tippingPoint: Long
    )

}
