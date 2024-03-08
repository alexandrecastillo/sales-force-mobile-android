package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos

import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoPorcentajeJoinned
import biz.belcorp.salesforce.core.utils.ceroSiNull
import biz.belcorp.salesforce.core.utils.negativoSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ComportamientoPorcentaje

class ComportamientoDetallePorcentajeMapper {

    fun map(list: List<ComportamientoPorcentajeJoinned>): List<ComportamientoPorcentaje> {
        return list.map { map(it) }
    }

    fun map(entity: ComportamientoPorcentajeJoinned): ComportamientoPorcentaje {
        return ComportamientoPorcentaje(
            descripcion = entity.descripcion.orEmpty(),
            iconoID = entity.iconoID.negativoSiNull(),
            porcentaje = entity.porcentaje.ceroSiNull()
        )
    }

}
