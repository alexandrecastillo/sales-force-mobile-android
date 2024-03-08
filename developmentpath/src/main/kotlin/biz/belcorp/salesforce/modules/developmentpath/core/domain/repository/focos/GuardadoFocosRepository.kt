package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFoco

interface GuardadoFocosRepository {
    fun obtenerDetallesNoEnviados(): List<DetalleFoco>

    fun marcarDetallesComoEnviados()

    fun guardar(detalles: List<DetalleFoco>)
}
