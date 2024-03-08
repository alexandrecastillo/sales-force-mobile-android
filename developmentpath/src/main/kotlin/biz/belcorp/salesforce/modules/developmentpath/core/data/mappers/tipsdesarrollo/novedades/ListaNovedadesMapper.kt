package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.entities.sql.novedades.NovedadesEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.DetalleNovedades
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Novedades

class ListaNovedadesMapper {

    fun parse(entidad: List<NovedadesEntity>): List<Novedades> {
        val grupos = entidad.groupBy { it.grupo }
        return grupos.map { parseGrupo(it.value) }
    }

    fun parseGrupo(grupo: List<NovedadesEntity>): Novedades {
        val titulo = grupo.firstOrNull()?.titulo.orEmpty()
        val detalle = grupo.map { parseDetalle(it) }
        return Novedades(titulo, detalle)
    }

    fun parseDetalle(detalle: NovedadesEntity): DetalleNovedades {
        return DetalleNovedades(detalle.tipoArchivo.orEmpty(), detalle.url.orEmpty())
    }
}
