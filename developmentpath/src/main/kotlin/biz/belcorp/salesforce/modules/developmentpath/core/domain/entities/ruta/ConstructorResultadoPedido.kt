package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session.ProcesadorPrefijoTelefonico

class ConstructorResultadoPedido(prefijoTelefonico: String) {

    private val procesadorPrefijo =
        ProcesadorPrefijoTelefonico(
            prefijoTelefonico
        )

    fun instanciarSinPedido(consultora: ConsultoraRdd): ResultadoPedido {
        return ResultadoPedido(
            consultora = consultora,
            montoPedido = Constant.ZERO_DECIMAL,
            numeroConPrefijo = procesadorPrefijo.obtenerGuionONumeroConPrefijo(consultora.directorio?.celular?.numero)
        )
    }
}
