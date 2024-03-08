package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsNegocioEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipsNegocio
import com.google.gson.Gson
import java.lang.reflect.Type

class TipsNegocioEntityMapper<T>(val type: Type, val gson: Gson) :
    Mapper<TipsNegocioEntity, TipsNegocio<T>>() {

    override fun map(value: TipsNegocioEntity): TipsNegocio<T> {
        val data = resolveJson(value.data, type)
        return TipsNegocio(
            personaId = value.personaId,
            region = value.region,
            zona = value.zona,
            seccion = value.seccion,
            opcion = value.opcion,
            data = data
        )
    }

    override fun reverseMap(value: TipsNegocio<T>) = TipsNegocioEntity()

    private fun resolveJson(json: String, type: Type): T? {
        return gson.fromJson<T>(json, type)
    }
}
