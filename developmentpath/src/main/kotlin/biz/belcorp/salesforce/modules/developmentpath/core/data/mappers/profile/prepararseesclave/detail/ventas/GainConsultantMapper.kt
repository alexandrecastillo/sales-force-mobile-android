package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultant

class GainConsultantMapper {
    fun map(entity: ConsultantSaleEntity) =
        GainConsultant(campaign = entity.campaign, amount = entity.gainAmount)

    fun map(entities: List<ConsultantSaleEntity>): List<GainConsultant> {
        return entities.map { map(it) }
    }
}
