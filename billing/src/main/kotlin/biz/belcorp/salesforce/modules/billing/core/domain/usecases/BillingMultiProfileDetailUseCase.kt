package biz.belcorp.salesforce.modules.billing.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.billing.core.domain.entities.BillingMultiProfileDetailContainer
import biz.belcorp.salesforce.modules.billing.core.domain.entities.BillingRules
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingDetailRepository

class BillingMultiProfileDetailUseCase(
    private val billingDetailRepository: BillingDetailRepository,
    private val sessionRepository: SessionRepository,
    private val campaignRepository: CampaniasRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getBillingDetailAdvancement(uaKey: LlaveUA): BillingMultiProfileDetailContainer {
        val ua = getUaKey(uaKey)
        val campaignCodes = getCampaignsCodes()

        val retainedPegs = getPegsRetainedOrders(ua, campaignCodes)
        val pendingNewCycle = getNewCyclePendingOrders(ua, campaignCodes)
        val isThirdPerson = !session.llaveUA.isEqual(uaKey)

        return BillingMultiProfileDetailContainer(retainedPegs, pendingNewCycle, isThirdPerson)
    }

    private fun getUaKey(uaKey: LlaveUA?) = uaKey ?: session.llaveUA

    private fun getCampaignsCodes(): List<String> {
        return campaignRepository.obtenerCampaniasSincrono(session.llaveUA)
            .take(BillingRules.MAXIMUM_CAMPAIGNS)
            .map { it.codigo }
    }

    private suspend fun getPegsRetainedOrders(
        uaKey: LlaveUA,
        campaignCodes: List<String>
    ): PegsBilling {
        val list = billingDetailRepository.getPegsRetainedOrders(uaKey, campaignCodes)
        return list.lastOrNull() ?: PegsBilling()
    }

    private suspend fun getNewCyclePendingOrders(
        uaKey: LlaveUA,
        campaignCodes: List<String>
    ): List<NewCycleBilling> {
        return billingDetailRepository.geNewCyclePendingOrders(uaKey, campaignCodes)
    }
}
