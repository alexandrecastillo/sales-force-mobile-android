package biz.belcorp.salesforce.modules.consultants.features.list.mappers

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel

class ConsultoraModelDataMapper {

    fun parseConsultant(consultora: Consultora): ConsultoraModel {
        return ConsultoraModel().parse(consultora)
    }

}
