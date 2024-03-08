package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd

class ResultadoPedido(
    val consultora: ConsultoraRdd,
    var montoPedido: Double,
    val numeroConPrefijo: String
) {

    val tienePedido: Boolean
        get() {
            return montoPedido > 0.0
        }

    fun quitarPedido() {
        montoPedido = 0.0
    }
}
