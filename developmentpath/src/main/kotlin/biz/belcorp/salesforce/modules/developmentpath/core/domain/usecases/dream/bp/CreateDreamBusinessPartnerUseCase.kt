package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.EditCreateDream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository

class CreateDreamBusinessPartnerUseCase(private val dreamBpRepository: DreamBpRepository) {

    suspend fun createBusinessPartnerDream(uaKey: LlaveUA, dream: EditCreateDream) {
        dreamBpRepository.createBusinessPartnerDream(
            uaKey,
            country = UserProperties.session?.countryIso!!, dream = dream
        )
    }
}
