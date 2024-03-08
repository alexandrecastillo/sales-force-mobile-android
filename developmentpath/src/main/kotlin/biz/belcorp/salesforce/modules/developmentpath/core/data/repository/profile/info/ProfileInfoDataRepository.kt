package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.BusinessPartnerInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.ConsultantInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.DirectoryInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.PostulantsInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers.ContactInfoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers.ProfileInfoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.ProfileInfoRepository


class ProfileInfoDataRepository(
    private val postulantsInfoDataStore: PostulantsInfoDataStore,
    private val consultantInfoDataStore: ConsultantInfoDataStore,
    private val partnerInfoDataStore: BusinessPartnerInfoDataStore,
    private val directoryInfoDataStore: DirectoryInfoDataStore,
    private val profileInfoMapper: ProfileInfoMapper,
    private val contactInfoMapper: ContactInfoMapper
) : ProfileInfoRepository {

    override suspend fun getPostulantInfo(id: Long): ProfileInfo {
        val postulant =
            requireNotNull(postulantsInfoDataStore.getPostulant(id)) { NOT_FOUND }
        val contact = contactInfoMapper.map(postulant)
        return profileInfoMapper.map(contact)
    }

    override suspend fun getConsultantInfo(code: String): ProfileInfo {
        val consultant =
            requireNotNull(consultantInfoDataStore.getConsultant(code)) { NOT_FOUND }
        val contact = contactInfoMapper.map(consultant)
        return profileInfoMapper.map(contact, consultant)
    }

    override suspend fun getBusinessPartnerInfo(code: String): ProfileInfo {
        val businesspartners =
            requireNotNull(partnerInfoDataStore.getBusinessPartner(code)) { NOT_FOUND }
        val contact = contactInfoMapper.map(businesspartners)
        return profileInfoMapper.map(contact, businesspartners)
    }

    private fun getManagerInfo(role: Rol, code: String): ProfileInfo {
        val manager = requireNotNull(directoryInfoDataStore.get(code)) { NOT_FOUND }
        val contact = contactInfoMapper.map(manager)
        return profileInfoMapper.map(role, contact, manager)
    }

    override suspend fun getZoneManagerInfo(code: String): ProfileInfo {
        return getManagerInfo(Rol.GERENTE_ZONA, code)
    }

    override suspend fun getRegionManagerInfo(code: String): ProfileInfo {
        return getManagerInfo(Rol.GERENTE_REGION, code)
    }

    companion object {

        private const val NOT_FOUND = "Person not found"

    }

}
