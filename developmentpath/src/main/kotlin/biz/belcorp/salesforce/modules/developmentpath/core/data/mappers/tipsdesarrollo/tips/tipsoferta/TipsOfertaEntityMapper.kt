package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.tipsoferta

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsoferta.OfertaEntity
import biz.belcorp.salesforce.core.utils.ColorUtils
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.TipOferta
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TipsOfertaEntityMapper(
    private val gson: Gson,
    private val innerMapper: OfertaEntityMapper
) : Mapper<TipOfertaEntity, TipOferta<List<Oferta>>>() {

    override fun map(value: TipOfertaEntity): TipOferta<List<Oferta>> {
        val data = resolveJsonData(value.data)
        val colores = resolveJsonColores(value.colores)
        return TipOferta(
            id = value.id,
            personaId = value.personaId,
            region = value.region,
            zona = value.zona,
            seccion = value.seccion,
            descripcion = value.descripcion,
            colores = colores.map(ColorUtils::crearTipoColor),
            orden = value.orden,
            data = innerMapper.map(data))
    }

    override fun reverseMap(value: TipOferta<List<Oferta>>) = TipOfertaEntity()

    private fun resolveJsonData(json: String): List<OfertaEntity> {
        val type = object : TypeToken<List<OfertaEntity>>() {}.type
        return gson.fromJson<List<OfertaEntity>>(json, type)
    }

    private fun resolveJsonColores(json: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson<List<Int>>(json, type)
    }
}
