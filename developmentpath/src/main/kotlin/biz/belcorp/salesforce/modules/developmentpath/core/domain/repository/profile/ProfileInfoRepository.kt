package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo

interface ProfileInfoRepository {

    suspend fun getPostulantInfo(id: Long): ProfileInfo

    suspend fun getConsultantInfo(code: String): ProfileInfo

    suspend fun getBusinessPartnerInfo(code: String): ProfileInfo

    suspend fun getZoneManagerInfo(code: String): ProfileInfo

    suspend fun getRegionManagerInfo(code: String): ProfileInfo

}
