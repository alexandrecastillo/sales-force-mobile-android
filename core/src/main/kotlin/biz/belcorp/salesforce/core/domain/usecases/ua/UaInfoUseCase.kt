package biz.belcorp.salesforce.core.domain.usecases.ua

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UARules
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isNotNull

class UaInfoUseCase(
    private val sessionRepository: SessionRepository,
    private val uaInfoRepository: UaInfoRepository,
    private val managerDirectoryRepository: ManagerDirectoryRepository,
    private val businessPartnerRepository: BusinessPartnerRepository,
    private val campaignsRepository: CampaniasRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun getAssociatedUaListByUaKey(
        uaKey: LlaveUA,
        excludeParent: Boolean = false
    ): List<UaInfo> {
        val uas = getUas(uaKey, excludeParent)
        val people = getPeople(uaKey.roleAssociated)
        uas.forEach { ua ->
            val person = people.firstOrNull { filters(it, ua) }
            val campaign = getCampaign()
            ua.person = person
            ua.isCovered = person.isNotNull()
            ua.campaign = campaign
        }
        return uas
    }

    private fun filters(person: Person, uaInfo: UaInfo): Boolean {
        return uaInfo.uaKey.codigoRegion == person.uaKey.codigoRegion
            && uaInfo.uaKey.codigoZona == person.uaKey.codigoZona
            && uaInfo.uaKey.codigoSeccion == person.uaKey.codigoSeccion
    }

    private suspend fun getUas(uaKey: LlaveUA, excludeParent: Boolean): List<UaInfo> {
        return uaInfoRepository.getAssociatedUaListByUaKey(uaKey, excludeParent)
            .filter { it.uaKey.codigoRegion !in UARules.UA_REGIONS_EXCLUDED }
    }

    private fun getCampaign(): Campania {
        return campaignsRepository.obtenerCampaniaActual(session.llaveUA)
            ?: Campania.construirDummy()
    }

    private suspend fun getPeople(role: Rol): List<Person> = with(role) {
        return when {
            isDV() || isGR() -> managerDirectoryRepository.getManagers()
            isGZ() -> businessPartnerRepository.getBusinessPartners()
            else -> listOf()
        }
    }
}
