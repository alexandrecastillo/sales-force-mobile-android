package biz.belcorp.salesforce.modules.brightpath.core.data.mapper.ua


import biz.belcorp.salesforce.core.domain.entities.ua.Region
import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity

class RegionEntityDataMapper {

    private fun parse(entity: RegionEntity) = Region().apply {
        codigo = entity.codigo
        gerenteRegion = entity.gerenteRegion
    }

    fun parse(collection: Collection<RegionEntity>): List<Region> {
        return mutableListOf<Region>().apply {
            for (entity in collection) {
                val region = parse(entity)
                add(region)
            }
        }
    }

}
