package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos

import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.Comportamiento

class ComportamientoMapper {
    fun convertir(modelo: ComportamientoEntity): Comportamiento {
        return Comportamiento(id = modelo.codigo,
            titulo = modelo.descripcion ?: Constant.EMPTY_STRING,
            tipoIcono = modelo.iconoId)
    }

    fun convertir(modelos: List<ComportamientoEntity>): List<Comportamiento> {
        return modelos.map { convertir(it) }
    }
}
