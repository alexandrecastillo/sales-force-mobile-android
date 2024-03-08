package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.TipoMeta
import java.util.*

class TipoMetaEntityDataMapper {

    fun parse(entity: TipoMetaEntity?): TipoMeta? {
        var pojo: TipoMeta? = null
        if (entity != null) {
            pojo = TipoMeta()
            pojo.idTipoMeta = entity.idTipoMeta
            pojo.codigoMeta = entity.codigoMeta
            pojo.descripcion = entity.descripcion
            pojo.montoMinimo = entity.montoMinimo
            pojo.montoMaximo = entity.montoMaximo
        }
        return pojo
    }

    fun parse(collection: Collection<TipoMetaEntity>): List<TipoMeta> {
        val list = ArrayList<TipoMeta>()
        var pojo: TipoMeta?
        for (entity in collection) {
            pojo = parse(entity)
            if (pojo != null) {
                list.add(pojo)
            }
        }
        return list
    }
}
