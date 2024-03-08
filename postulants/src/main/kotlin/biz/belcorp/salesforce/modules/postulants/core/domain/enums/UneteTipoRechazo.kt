package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteTipoRechazo(val desc: String, val tipo: Int) {

    VALIDACIO_CREDITICIA("Rechazada por Validación Crediticia", 1),
    YA_POSTULANTE("Rechazada porque ya es Postulante", 2),
    YA_CONSULTORA("Rechazada porque ya es Consultora", 3),
    BLOQUEO_INTERNO("Rechazada por Bloqueos Internos", 4),
    VALIDACION_TLF("Rechazada por Validación Telefónica", 5),
    ZONA_PREFERENCIAL("Rechazada por Zona Preferencial", 6),
    GERENTE_ZONA("Rechazada por Gerente de Zona", 7),
    SE("Rechazada por Socia Empresaria", 8),
    SAC("Rechazada por SAC", 9),
    SE_WEB("Rechazada por Socia Empresaria Web", 10),
    CC("Rechazada por CC", 11),
    PROACTIVA_RECHAZADA("Pase de pedido rechazado", 12);

    companion object {

        fun find(tipo: Int): UneteTipoRechazo? {
            values().forEach {
                if (it.tipo == tipo)
                    return it
            }
            return null
        }
    }
}
