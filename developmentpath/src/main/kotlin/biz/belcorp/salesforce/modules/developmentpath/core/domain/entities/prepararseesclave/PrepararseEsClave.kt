package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave

data class PrepararseEsClave(
    val id: Int,
    val titulo: String,
    val orden: Int,
    val tipo: Tipo,
    val esVisible: Boolean
) {
    enum class Tipo { RESULTADOCX, NEGOCIO, DIGITAL, ACUERDOSU3C, VENTA, LOMASVENDIDO }
}
