package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream

import androidx.lifecycle.ViewModel
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties.session
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream.Dream
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream.DreamRepository

class DeleteDreamUseCase(
    private val dreamRepository: DreamRepository,
) : ViewModel() {

    suspend fun deleteDream(dream: Dream?) =
        dreamRepository.deleteDream(dream, session?.countryIso)
}
