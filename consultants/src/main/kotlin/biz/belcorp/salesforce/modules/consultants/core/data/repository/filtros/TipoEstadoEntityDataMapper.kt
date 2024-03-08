package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoEstadoEntity

class TipoEstadoEntityDataMapper {

    fun parse(entity: TipoEstadoEntity?): TipoEstado? {
        var pojo: TipoEstado? = null

        if (entity != null) {
            pojo = TipoEstado()
            pojo.idEstadoActividad = entity.idEstadoActividad
            pojo.descripcion = entity.descripcion
            pojo.estadoActivo = entity.estadoActivo
        }

        return pojo
    }

    fun parse(collection: Collection<TipoEstadoEntity>): List<TipoEstado> {
        val list = ArrayList<TipoEstado>()
        var pojo: TipoEstado?
        for (entity in collection) {
            pojo = parse(entity)
            if (pojo != null) {
                list.add(pojo)
            }
        }
        return list
    }

}
