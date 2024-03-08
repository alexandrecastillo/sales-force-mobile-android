package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class TipoPago(val id: Int) {
    CREDITO(0),
    CONTADO(1);

    companion object {
        fun find(id: Int): TipoPago? {
            values().forEach {
                if (it.id == id)
                    return it
            }
            return null
        }
    }

}
