package biz.belcorp.salesforce.core.data.repository.uainfo.mappers

import biz.belcorp.salesforce.core.entities.sql.ua.ZonaEntity
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.utils.SingleMapper

class ZonaTableMapper : SingleMapper<UaEntity, ZonaEntity>() {

    override fun map(value: UaEntity) = ZonaEntity().apply {
        codigo = value.zone
        region = value.region
        gerenteZona = value.consultantName
    }

}
