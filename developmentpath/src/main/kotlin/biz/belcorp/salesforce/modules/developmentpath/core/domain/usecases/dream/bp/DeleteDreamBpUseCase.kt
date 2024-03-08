package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository

class DeleteDreamBpUseCase(private val dreamBpRepository: DreamBpRepository) {

    suspend fun deleteBusinessPartnerDream(dream: Dream?) =
        dreamBpRepository.deleteBusinessPartnerDream(dream, UserProperties.session?.countryIso)
}
