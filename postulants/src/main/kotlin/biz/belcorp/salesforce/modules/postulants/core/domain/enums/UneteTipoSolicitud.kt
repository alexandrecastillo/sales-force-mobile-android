package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteTipoSolicitud(val value: String, val desc: String) {
    INS("INS", "Nueva"),
    REINGRESO("AD", "Reingreso");

    companion object {
        fun find(desc: String): UneteTipoSolicitud? {
            values().forEach {
                if (it.desc == desc)
                    return it
            }
            return null
        }
    }
}
