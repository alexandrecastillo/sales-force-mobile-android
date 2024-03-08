package biz.belcorp.salesforce.core.data.repository.uainfo.mappers

import biz.belcorp.salesforce.core.entities.sql.ua.RegionEntity
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.utils.SingleMapper

class RegionTableMapper : SingleMapper<UaEntity, RegionEntity>() {

    override fun map(value: UaEntity) = RegionEntity().apply {
        codigo = value.region
        gerenteRegion = value.consultantName
    }

}
