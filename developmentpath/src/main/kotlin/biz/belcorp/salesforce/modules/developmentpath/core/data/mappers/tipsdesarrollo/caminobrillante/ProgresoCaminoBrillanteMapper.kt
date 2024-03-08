package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelActualCaminoBrillanteEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelesCaminoBrillanteEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelActualCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelCaminoBrillante
import com.google.gson.Gson

class ProgresoCaminoBrillanteMapper(gson: Gson) : ProgresoCaminoBrillanteMapperBase(gson) {

    fun parse(entidad: NivelActualCaminoBrillanteEntity): NivelActualCaminoBrillante {
        return NivelActualCaminoBrillante(
            llaveUA = crearUa(entidad),
            nivelActual = entidad.nivelActual,
            progreso = entidad.progreso,
            meta = entidad.meta,
            hito = entidad.hito,
            tips = crearTipsProgresoCaminoBrillante(entidad.data))
    }

    fun parse(entidad: NivelesCaminoBrillanteEntity): NivelCaminoBrillante {
        return NivelCaminoBrillante(
            id = entidad.codigo,
            titulo = entidad.nombre,
            orden = entidad.orden)
    }

    private fun crearUa(entidad: NivelActualCaminoBrillanteEntity): LlaveUA {
        return LlaveUA(
            consultoraId = entidad.consultoraId,
            codigoRegion = entidad.region,
            codigoZona = entidad.zona,
            codigoSeccion = entidad.seccion
        )
    }

}
