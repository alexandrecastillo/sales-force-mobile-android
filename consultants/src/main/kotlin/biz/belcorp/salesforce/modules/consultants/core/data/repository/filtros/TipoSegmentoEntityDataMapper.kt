package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSegmentoEntity

class TipoSegmentoEntityDataMapper {

    fun parse(entity: TipoSegmentoEntity?): TipoSegmento? {
        var pojo: TipoSegmento? = null

        if (entity != null) {
            pojo = TipoSegmento()
            pojo.segmentoInternoId = entity.segmentoInternoId
            pojo.descripcion = entity.descripcion
            pojo.abreviatura = entity.abreviatura
            pojo.estadoActivo = entity.estadoActivo
        }

        return pojo
    }

    fun parse(collection: Collection<TipoSegmentoEntity>): List<TipoSegmento> {
        val list = ArrayList<TipoSegmento>()
        var pojo: TipoSegmento?
        for (entity in collection) {
            pojo = parse(entity)
            if (pojo != null) {
                list.add(pojo)
            }
        }
        return list
    }

}
