package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteEstadoCrediticio(val value: Int) {

    ERROR_INTERNO(-1),
    SIN_CONSULTAR(1),
    PUEDE_SER_CONSULTORA(2),
    NO_PUEDE_SER_CONSULTORA(3),
    PODRIA_SER_CONSULTORA(4),
    ERROR(5),
    CONDICIONADA_FIADOR(30),
    CONDICIONADA_CARTA_DESCARGO(31),
    CARTA_DESCARGO_Y_FIADOR(32),
    CONDICIONADA(33),
    SUJETO_NO_ENCONTRADO(37);

    companion object {

        fun find(value: Int?) = values().find { it.value == value }

        val UneteRechazoCrediticio= listOf(
            "ERR_INVALID_PARAMS",
            "ERR_IDENTIFICATION_INVALID",
            "ERR_SOLICITUD_RECHAZADA"
        )
    }

}
