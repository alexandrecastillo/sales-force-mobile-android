package biz.belcorp.salesforce.core.constants

object OrderStatusSic {

    private const val SIN_PEDIDO = 0
    const val FACTURADO = 1
    private const val RECHAZADO = 2

    val notBilledOrdersFilter get() = listOf(
        SIN_PEDIDO,
        RECHAZADO
    )

}
