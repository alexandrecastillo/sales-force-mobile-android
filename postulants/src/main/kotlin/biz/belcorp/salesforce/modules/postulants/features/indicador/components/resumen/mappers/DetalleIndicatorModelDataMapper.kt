package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.mappers

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicador
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorModel
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.entities.DetalleIndicadorUneteModel
import java.util.*

class DetalleIndicatorModelDataMapper {
    fun parse(collection: Collection<DetalleIndicador>?): List<DetalleIndicadorModel> {
        val list: MutableList<DetalleIndicadorModel>

        if (collection != null && !collection.isEmpty()) {
            list = ArrayList()
            for (entity in collection) {
                list.add(DetalleIndicadorUneteModel().parse(entity))
            }
        } else {
            list = emptyList<DetalleIndicadorModel>().toMutableList()
        }

        return list
    }

}
