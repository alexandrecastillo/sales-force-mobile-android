package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona

import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo

class DatosPersonaMapper {

    fun map(datos: ProfileInfo): DatosPersonaModel {
        return when (datos) {
            is ProfileInfo.DatoPersonaGz -> {
                val ua = "Zona ${datos.llaveUA?.codigoZona.guionSiNull()}"
                val titulo = "Estado C${datos.prevCampaign}"
                DatosPersonaModel(titulo = titulo, ua = ua, estado = datos.estado)
            }
            is ProfileInfo.DatoPersonaGr -> {
                val ua = "Zona ${datos.llaveUA?.codigoRegion.guionSiNull()}"
                val titulo = "Estado C${datos.prevCampaign}"
                DatosPersonaModel(titulo = titulo, ua = ua, estado = datos.estado)
            }
            else -> throw Exception("Rol no soportado")
        }
    }

}
