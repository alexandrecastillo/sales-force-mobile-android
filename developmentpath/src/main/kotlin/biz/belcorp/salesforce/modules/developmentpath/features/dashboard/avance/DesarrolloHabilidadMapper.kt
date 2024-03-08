package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance

import biz.belcorp.salesforce.core.entities.sql.habilidades.DesarrolloHabilidadEntity
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DesarrolloHabilidad
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.model.DesarrolloHabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view.ProveedorEstiloHabilidad

class DesarrolloHabilidadMapper {

    private fun parseEntity(entity: DesarrolloHabilidadEntity): DesarrolloHabilidad {
        return DesarrolloHabilidad(
            descripcion = entity.descripcion ?: Constant.EMPTY_STRING,
            porcentaje = entity.porcentaje,
            iconoId = entity.iconoId
        )
    }

    fun parseList(list: List<DesarrolloHabilidad>): List<DesarrolloHabilidadModel> {
        return list.map { parse(it) }
    }

    fun parse(entity: DesarrolloHabilidad): DesarrolloHabilidadModel {
        return DesarrolloHabilidadModel(
            descripcion = entity.descripcion,
            porcentaje = entity.porcentaje,
            colorResId = obtenerColorResId(entity.porcentaje),
            iconResId = obtenerIconoSimple(entity.iconoId)
        )
    }

    private fun obtenerColorResId(porcentaje: Int): Int {
        return if (porcentaje <= Constant.HABILIDADES_PERCENTAGE_LIMIT) {
            R.color.mi_ruta_danger
        } else {
            R.color.estado_positivo
        }
    }

    private fun obtenerIconoSimple(iconId: Int): Int {
        return ProveedorEstiloHabilidad().obtenerIcono(iconId)
    }

    fun parseEntityList(entityList: List<DesarrolloHabilidadEntity>): List<DesarrolloHabilidad> {
        return entityList.map { parseEntity(it) }
    }

}
