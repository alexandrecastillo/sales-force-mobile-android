package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteEstadoActividad(val value: Int) {
    NUEVA(2),
    CONSTANTE(3),
    POSIBLE_EGRESO(4),
    EGRESO(5),
    REINGRESO(6),
    RETIRADA(7),
    REACTIVADA(8),
    PENDIENTE(9)

}
