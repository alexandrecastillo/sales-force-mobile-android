package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.modules.developmentpath.core.data.entities.tipsdesarrollo.caminobrillante.TipCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.TipProgresoCaminoBrillante
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class ProgresoCaminoBrillanteMapperBase(private val gson: Gson) {

    fun crearTipsProgresoCaminoBrillante(data: String): List<TipProgresoCaminoBrillante> {
        val tips = crearTipDataCustom(data)
        return tips.map { parse(it) }
    }

    private fun crearTipDataCustom(data: String): List<TipCaminoBrillante> {
        val customType = object : TypeToken<List<TipCaminoBrillante>>() {}.type
        return gson.fromJson<List<TipCaminoBrillante>>(data, customType)
    }


    private fun parse(entidad: TipCaminoBrillante): TipProgresoCaminoBrillante {
        return TipProgresoCaminoBrillante(
            texto = entidad.titulo,
            icono = entidad.tipoIcono,
            colores = entidad.tipoColores)
    }

}
