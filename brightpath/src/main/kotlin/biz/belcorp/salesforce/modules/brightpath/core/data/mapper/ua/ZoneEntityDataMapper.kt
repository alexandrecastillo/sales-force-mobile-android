package biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua

import biz.belcorp.salesforce.core.domain.entities.ua.Zona
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity

class ZoneEntityDataMapper {

    private fun parse(entity: ZonaEntity) = Zona().apply {
        codigo = entity.codigo
        gerenteZona = entity.gerenteZona
    }

    fun parse(collection: Collection<ZonaEntity>): List<Zona> {
        return mutableListOf<Zona>().apply {
            for (entity in collection) {
                val zone = parse(entity)
                add(zone)
            }
        }
    }
}
