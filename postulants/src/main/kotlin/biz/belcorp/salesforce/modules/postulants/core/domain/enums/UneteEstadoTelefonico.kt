package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteEstadoTelefonico(val value: Int) {
    POR_VALIDAR(1),
    VALIDADO(2),
    NO_REQUERIDO(3),
    REQUIERE_VALIDACION_GZ(5),
    VALIDADO_POR_GZ(6),
    ZERO(0)
}
