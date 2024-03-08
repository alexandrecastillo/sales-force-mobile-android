package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.ProfileInfoRepository


class GetProfileInfoUseCase(
    private val profileInfoRepository: ProfileInfoRepository,
    private val configurationRepository: ConfigurationRepository,
    private val campaignRepository: CampaniasRepository
) {

    suspend fun getInfo(personIdentifier: PersonIdentifier): ProfileInfo {
        val profileInfo = when {
            personIdentifier.role.isPC() -> getPostulantInfo(personIdentifier.id)
            personIdentifier.role.isCO() -> getConsultantInfo(personIdentifier.code)
            personIdentifier.role.isSE() -> getBusinessPartnerInfo(personIdentifier.code)
            personIdentifier.role.isGZ() -> getZoneManagerInfo(personIdentifier.code)
            personIdentifier.role.isGR() -> getRegionManagerInfo(personIdentifier.code)
            else -> throw UnsupportedRoleException(personIdentifier.role)
        }
        return profileInfo.setupCountryPrefix()
    }

    private suspend fun getPostulantInfo(id: Long): ProfileInfo {
        return profileInfoRepository.getPostulantInfo(id)
    }

    private suspend fun getConsultantInfo(code: String): ProfileInfo {
        return profileInfoRepository.getConsultantInfo(code)
    }

    private suspend fun getBusinessPartnerInfo(code: String): ProfileInfo {
        return profileInfoRepository.getBusinessPartnerInfo(code)
    }

    private suspend fun getZoneManagerInfo(code: String): ProfileInfo {
        return profileInfoRepository.getZoneManagerInfo(code)
            .setupPrevCampaign()
    }

    private suspend fun getRegionManagerInfo(code: String): ProfileInfo {
        return profileInfoRepository.getRegionManagerInfo(code)
            .setupPrevCampaign()
    }

    private suspend fun getPrevCampaign(llaveUA: LlaveUA): Campania {
        return campaignRepository.getPreviousCampaignSuspend(llaveUA)
    }

    private suspend fun ProfileInfo.setupCountryPrefix() = apply {
        contact.prefix = configurationRepository.getConfiguration(llaveUA).phoneCode
    }

    private suspend fun ProfileInfo.setupPrevCampaign() = apply {
        val campaign = getPrevCampaign(llaveUA ?: return@apply)
        when (this) {
            is ProfileInfo.DatoPersonaGz -> {
                prevCampaign = campaign.obtenerNombreNumerico()
            }
            is ProfileInfo.DatoPersonaGr -> {
                prevCampaign = campaign.obtenerNombreNumerico()
            }
        }
    }

}
