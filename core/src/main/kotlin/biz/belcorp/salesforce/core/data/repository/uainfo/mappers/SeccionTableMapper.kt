package biz.belcorp.salesforce.core.data.repository.uainfo.mappers

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import biz.belcorp.salesforce.core.entities.ua.UaEntity
import biz.belcorp.salesforce.core.utils.SingleMapper

class SeccionTableMapper : SingleMapper<UaEntity, SeccionEntity>() {

    override fun map(value: UaEntity) = SeccionEntity().apply {
        region = value.region
        zona = value.zone
        seccionId = value.id.toInt()
        codigo = value.section
        descripcion = value.description
        consultoraCodigo = value.consultantId ?: EMPTY_STRING
        sociaEmpresaria = value.consultantName
    }

}
