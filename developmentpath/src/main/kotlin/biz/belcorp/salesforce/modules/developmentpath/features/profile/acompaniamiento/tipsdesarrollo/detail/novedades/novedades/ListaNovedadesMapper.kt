package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DetalleNovedadesMapper

class ListaNovedadesMapper {

    fun map(novedades: List<Novedades>): List<ListadoNovedadesModel> {
        return novedades.map { parse(it) }
    }

    private fun parse(novedades: Novedades): ListadoNovedadesModel {
        return ListadoNovedadesModel(
            novedades.titulo,
            DetalleNovedadesMapper().reverseMap(novedades.detalleNovedades)
        )
    }
}
