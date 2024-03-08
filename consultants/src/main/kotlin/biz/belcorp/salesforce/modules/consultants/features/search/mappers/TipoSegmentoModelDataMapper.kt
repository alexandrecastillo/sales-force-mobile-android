package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel


class TipoSegmentoModelDataMapper {

    fun parse(collection: Collection<TipoSegmento>?): List<TipoSegmentoModel> {
        return if (collection != null && !collection.isEmpty()) {
            collection.mapTo(ArrayList()) { TipoSegmentoModel().parse(it) }

        } else {
            emptyList()
        }
    }

    fun parse(pojo: TipoSegmento): TipoSegmentoModel {
        return TipoSegmentoModel().parse(pojo)
    }

}
