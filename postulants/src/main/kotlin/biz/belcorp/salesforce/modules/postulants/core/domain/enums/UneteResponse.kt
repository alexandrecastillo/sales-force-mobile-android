package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteResponse {

    SUCCESS,
    ERROR_GENERIC,
    ERROR_SERVICE,

    ES_CONSULTORA,
    ES_POSTULANTE_RECHAZADA,
    ES_POSTULANTE,
    ES_POTENCIAL,
    BLOQUEOS_INTERNOS,
    BLOQUEOS_EXTERNOS,

    SIN_MAPA,
    SIN_ZONA_SECCION,
    CON_ZONA_SECCION_DIFERENTE,

    REINTENTAR,
    FINISH

}
