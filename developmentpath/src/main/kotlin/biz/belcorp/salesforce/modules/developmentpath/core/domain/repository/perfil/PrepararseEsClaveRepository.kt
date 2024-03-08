package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave

interface PrepararseEsClaveRepository {
    fun obtenerElementosPorRol(rol: Rol): List<PrepararseEsClave>
    fun reemplazarCampaniaEnElementoSocia(campania: String, elementos: List<PrepararseEsClave>): List<PrepararseEsClave>
}
