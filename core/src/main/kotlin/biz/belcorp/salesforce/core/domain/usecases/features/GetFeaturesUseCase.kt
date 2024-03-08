package biz.belcorp.salesforce.core.domain.usecases.features

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE


class GetFeaturesUseCase(
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    fun getFeaturesByRole(): List<String> = with(session.rol) {
        return when {
            isDV() -> getFeaturesForDV()
            isGR() -> getFeaturesForGR()
            isGZ() -> getFeaturesForGZ()
            isSE() -> getFeaturesForSE()
            else -> emptyList()
        }
    }

    private fun getFeaturesForDV() = with(DynamicFeature) {
        listOf(
            DEVELOPMENT_MATERIAL,
            DEVELOPMENT_PATH,
            POLITICS_TERMS_CONDITIONS
        )
    }

    private fun getFeaturesForGR() = with(DynamicFeature) {
        listOf(
            CREDIT_INQUIRY,
            VIRTUALMETHODOLOGY,
            DEVELOPMENT_MATERIAL,
            DEVELOPMENT_PATH,
            POLITICS_TERMS_CONDITIONS
        )
    }

    private fun getFeaturesForGZ() = with(DynamicFeature) {
        listOf(
            CONSULTANTS,
            ORDERS,
            POSTULANTS,
            CREDIT_INQUIRY,
            VIRTUALMETHODOLOGY,
            DEVELOPMENT_MATERIAL,
            DEVELOPMENT_PATH,
            BRIGHTPATH,
            POLITICS_TERMS_CONDITIONS
        )
    }

    private fun getFeaturesForSE() = with(DynamicFeature) {
        listOf(
            CONSULTANTS,
            ORDERS,
            POSTULANTS,
            VIRTUALMETHODOLOGY,
            DEVELOPMENT_MATERIAL,
            DEVELOPMENT_PATH,
            BRIGHTPATH,
            CALCULATOR,
            POLITICS_TERMS_CONDITIONS,
            INSPIRES
        )
    }

}
