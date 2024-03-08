package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel


class TipoEstadoModelDataMapper {

    fun parse(collection: Collection<TipoEstado>?): List<TipoEstadoModel> {
        return if (collection != null && !collection.isEmpty()) {
            collection.mapTo(ArrayList()) { TipoEstadoModel().parse(it) }
        } else {
            emptyList()
        }
    }

    fun parse(pojo: TipoEstado): TipoEstadoModel {
        return TipoEstadoModel().parse(pojo)
    }

}
