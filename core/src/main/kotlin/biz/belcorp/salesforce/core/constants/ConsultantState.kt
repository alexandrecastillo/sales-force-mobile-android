package biz.belcorp.salesforce.core.constants

object ConsultantState {

    private const val EGRESADA = "5"
    private const val RETIRADA = "7"
    private const val REGISTRADA = "1"
    const val INGRESO = "2"
    const val REINGRESO = "6"

    val inactivesFilter get() = listOf(
        EGRESADA,
        RETIRADA,
        REGISTRADA
    )

}
