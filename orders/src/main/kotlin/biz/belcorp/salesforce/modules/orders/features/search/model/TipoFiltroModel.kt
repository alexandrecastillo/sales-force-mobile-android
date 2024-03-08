package biz.belcorp.salesforce.modules.orders.features.search.model


class TipoFiltroModel(var codigo: String? = null, var descripcion: String? = null) {

    companion object {

        const val NO_VALIDADOS = "No Validados"
        const val NO_VALIDADOS_CODIGO = "1"
        const val VALIDADOS = "Validados"
        const val VALIDADOS_CODIGO = "2"
        const val RECHAZADOS = "Rechazados"
        const val RECHAZADOS_CODIGO = "3"
        const val FACTURADOS = "Facturados"
        const val FACTURADOS_CODIGO = "4"

    }

}
