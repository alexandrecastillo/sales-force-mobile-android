package biz.belcorp.salesforce.modules.digital.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalConsolidated
import biz.belcorp.salesforce.modules.digital.core.domain.entities.DigitalInfo
import biz.belcorp.salesforce.modules.digital.core.domain.repository.DigitalRepository

class GetDigitalInfoUseCase(
    private val sessionRepository: SessionRepository,
    private val digitalRepository: DigitalRepository,
    private val uaInfoUseCase: UaInfoUseCase
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun getDigitalInfo(uaKey: LlaveUA): DigitalInfo {
        val campaign = session.campaign.codigo
        return digitalRepository.getDigitalInfo(campaign, uaKey)
    }

    suspend fun getDigitalConsolidated(uaKey: LlaveUA): DigitalConsolidated {
        val campaign = session.campaign
        val uaList = getUas(uaKey)
        val digitalInfolist = getDigitalInfoByParent(campaign.codigo, uaKey)
        return DigitalConsolidated(
            digitalInfolist = digitalInfolist,
            uaList = uaList,
            campaign = campaign.shortNameOnly,
            role = uaKey.roleAssociated,
            isThirdPerson = !uaKey.isEqual(session.llaveUA)
        )
    }

    private suspend fun getUas(uaKey: LlaveUA): List<UaInfo> {
        return uaInfoUseCase.getAssociatedUaListByUaKey(uaKey)
    }

    private suspend fun getDigitalInfoByParent(
        campaign: String,
        uaKey: LlaveUA
    ): List<DigitalInfo> {
        return digitalRepository.getDigitalInfoByParent(campaign, uaKey)
    }

}
