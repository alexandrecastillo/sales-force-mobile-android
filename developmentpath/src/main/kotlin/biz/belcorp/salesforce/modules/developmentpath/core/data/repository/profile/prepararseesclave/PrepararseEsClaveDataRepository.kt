package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.PrepararseEsClaveProvider
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.PrepararseEsClaveRepository

class PrepararseEsClaveDataRepository : PrepararseEsClaveRepository {

    override fun obtenerElementosPorRol(rol: Rol): List<PrepararseEsClave> {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA -> PrepararseEsClaveProvider.obtenerElementosSocia()
            Rol.CONSULTORA -> PrepararseEsClaveProvider.obtenerElementosConsultora()
            Rol.DIRECTOR_VENTAS,
            Rol.GERENTE_REGION,
            Rol.GERENTE_ZONA,
            Rol.POSIBLE_CONSULTORA,
            Rol.NINGUNO -> PrepararseEsClaveProvider.obtenerElementosOtrosRoles()
        }
    }

    override fun reemplazarCampaniaEnElementoSocia(
        campania: String,
        elementos: List<PrepararseEsClave>
    ): List<PrepararseEsClave> {
        return PrepararseEsClaveProvider.reemplazarCampaniaEnElementosSocia(campania, elementos)
    }
}
