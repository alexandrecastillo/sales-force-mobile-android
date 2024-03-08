package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.DreamBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream

interface DreamBpRepository {
    suspend fun syncBusinessPartnerDreams(uaKey: LlaveUA, country: String)

    suspend fun getBusinessPartnerDream(
        businessPartnerCode: String?,
        uaKey: LlaveUA
    ): List<DreamBusinessPartner>

    suspend fun createBusinessPartnerDream(uaKey: LlaveUA, country: String, dream: EditCreateDream)

    suspend fun deleteBusinessPartnerDream(dream: Dream?, country: String?)

    suspend fun editBusinessPartnerDreams(
        uaKey: LlaveUA, country: String, dream: EditCreateDream,
        campaign: String
    )

}
