package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad

class ZonasProductivasMapper {

    fun map(productivas: ZonasProductividad): List<ZonaProductivaModel> {
        val cabecera = mapCabecera(productivas)
        val detalles = productivas.zonasProductividad.map { map(it) }.toMutableList()
        detalles.add(0, cabecera)
        return detalles
    }

    private fun map(zonaProductiva: ZonasProductividad.ZonaProductividad): ZonaProductivaModel {
        return ZonaProductivaModel(
            zona = "GZ ${zonaProductiva.zona}",
            productividadCampaniaUltima = mapProductividad(
                zonaProductiva.productividadUltimasCampanias.getOrNull(0)
            ),
            productividadCampaniaPenultima = mapProductividad(
                zonaProductiva.productividadUltimasCampanias.getOrNull(1)
            ),
            productividadCampaniaAntepenultima = mapProductividad(
                zonaProductiva.productividadUltimasCampanias.getOrNull(2)
            )
        )
    }

    private fun mapCabecera(productivas: ZonasProductividad): ZonaProductivaModel {
        return ZonaProductivaModel(
            zona = "ZONA",
            productividadCampaniaUltima = mapCampania(productivas.campanias.getOrNull(0)),
            productividadCampaniaPenultima = mapCampania(productivas.campanias.getOrNull(1)),
            productividadCampaniaAntepenultima = mapCampania(productivas.campanias.getOrNull(2))
        )
    }

    private fun mapProductividad(productividad: ZonasProductividad.Productividad?):
        ProductividadOCampaniaModel {
        return ProductividadOCampaniaModel.Builder().construir(productividad?.abreviacion ?: "-")
    }

    private fun mapCampania(campania: String?): ProductividadOCampaniaModel {
        return ProductividadOCampaniaModel.Builder().construir(campania ?: "-")
    }
}
