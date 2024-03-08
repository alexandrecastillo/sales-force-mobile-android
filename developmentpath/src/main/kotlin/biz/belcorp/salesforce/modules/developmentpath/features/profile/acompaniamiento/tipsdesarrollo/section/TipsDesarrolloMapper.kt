package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipDataDetalle
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.helper.AcompaniamientoResourcesProvider

class TipsDesarrolloMapper {

    fun parse(entidad: TipDesarrollo): List<TipDesarrolloModel> {
        return entidad.data.mapIndexed { indice: Int, tipData: TipData -> parse(tipData, indice) }
    }

    private fun parse(entidad: TipData, indice: Int): TipDesarrolloModel {
        return TipDesarrolloModel(
            id = entidad.id,
            imagen = AcompaniamientoResourcesProvider.fromTipDesarrolloIconId(entidad.imagen.valor),
            tipoImagen = entidad.imagen,
            tituto = entidad.titulo,
            posicion = indice,
            tipo = entidad.tipo,
            opciones = entidad.opciones,
            detalles = entidad.detalles.map { parseDataDetalle(it) }
        )
    }

    private fun parseDataDetalle(entidad: TipDataDetalle): TipDesarrolloModel.Detalle {
        return TipDesarrolloModel.Detalle(
            descripcion = entidad.texto,
            colores = entidad.colores.map { AcompaniamientoResourcesProvider.fromColor(it) })
    }
}
