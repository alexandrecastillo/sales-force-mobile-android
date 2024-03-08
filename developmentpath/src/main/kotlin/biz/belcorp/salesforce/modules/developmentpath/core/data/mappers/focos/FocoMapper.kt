package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos

import biz.belcorp.salesforce.core.entities.sql.focos.TituloDetalleFocoJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.focos.DetalleFocoSEApiModel
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import com.google.gson.Gson

class FocoMapper {

    private val gson = Gson()

    fun parse(models: List<TituloDetalleFocoJoinned>): List<CabeceraFoco> {
        val cabeceraList = mutableListOf<CabeceraFoco>()
        lateinit var cabecera: CabeceraFoco
        var tituloPasoId = 0
        models.forEach {
            if (tituloPasoId != it.tituloPasoId) {
                cabecera = CabeceraFoco()
                cabecera.nombre = it.nombreTitulo
                cabecera.descripcion = it.descripcionTitulo
                cabecera.rutaImagen = it.rutaImagen
                cabecera.descripcionDetalle = it.descripcionDetalle
                cabeceraList.add(cabecera)
                tituloPasoId = it.tituloPasoId
            } else {
                cabecera.descripcionDetalle += String.format("\n\n%s", it.descripcionDetalle)
            }
        }
        return cabeceraList
    }

    fun parseToRequest(focos: List<DetalleFocoSE>): List<DetalleFocoSEApiModel> {
        return focos.map {
            DetalleFocoSEApiModel(
                consultoraID = it.personaId,
                focos = gson.toJson(it.focos),
                campania = it.campania)
        }
    }
}
