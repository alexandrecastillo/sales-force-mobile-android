package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo
import com.google.gson.Gson

class TipsDesarrolloMapper(gson: Gson) : TipsDesarrolloMapperBase(gson) {

    override fun parse(entidad: TipsDesarrolloEntity): TipDesarrollo {
        return TipDesarrollo(
            personaId = entidad.personaId,
            region = entidad.region,
            zona = entidad.zona,
            seccion = entidad.seccion,
            data = crearData(entidad.data))
    }

    override fun parse(entidades: List<TipsDesarrolloEntity>): List<TipDesarrollo> {
        return entidades.map { parse(it) }
    }
}
