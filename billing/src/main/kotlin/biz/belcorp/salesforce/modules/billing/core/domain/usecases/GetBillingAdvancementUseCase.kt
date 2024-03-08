package biz.belcorp.salesforce.modules.billing.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.domain.entities.configuration.ConfigurationRules
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.core.utils.isMultiProfile
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingRepository

class GetBillingAdvancementUseCase(
    private val repository: BillingRepository,
    private val sessionRepository: SessionRepository,
    private val configRepository: ConfigurationRepository,
    private val businessPartnerRepository: BusinessPartnerRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getBillingAdvancement(uaKey: LlaveUA): Billing {
        val currentRole = uaKey.roleAssociated
        val config = configRepository.getConfiguration(uaKey)
        val billing = repository.getBillingAdvancement(uaKey)

        return billing.apply {
            currencySymbol = config.currencySymbol
            isBright = config.isPdv && isLevelPdv(uaKey)
            isThirdPerson = currentRole != session.rol
        }
    }

    private suspend fun isLevelPdv(uaKey: LlaveUA): Boolean {
        if (uaKey.roleAssociated.isMultiProfile()) return false
        val levels = ConfigurationRules.getBrightLevelKpi(session.pais)
        val person = getBusinessPartner(uaKey) as? BusinessPartner
        return person?.let { person.levelType in levels } ?: false
    }

    private suspend fun getBusinessPartner(uaKey: LlaveUA): Person? {
        return if (!uaKey.isEqual(session.llaveUA)) {
            businessPartnerRepository.getBusinessPartner(uaKey)
        } else {
            session.person
        }
    }

}
