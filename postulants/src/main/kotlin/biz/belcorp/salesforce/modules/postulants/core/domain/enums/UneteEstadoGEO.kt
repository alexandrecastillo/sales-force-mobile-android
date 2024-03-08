package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteEstadoGEO(val value: Int) {
    SIN_CONSULTAR(1),
    OK(2),
    NOENCONTROTERRITORIONOLATLONG(3),
    NOENCONTROTERRITORIOSILATLONG(4),
    ERRORCONSUMOINTEGRACION(5);
}
