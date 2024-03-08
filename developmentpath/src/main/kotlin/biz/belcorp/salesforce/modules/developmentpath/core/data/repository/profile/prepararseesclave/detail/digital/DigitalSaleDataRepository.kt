package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.DigitalSaleDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper.DigitalSaleMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSale
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleBusinessPartner
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.digital.DigitalSaleConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DigitalSaleRepository

class DigitalSaleDataRepository(
    private val digitalSaleDataStore: DigitalSaleDataStore,
    private val digitalSaleMapper: DigitalSaleMapper
) : DigitalSaleRepository {

    override suspend fun getDigitalSaleByRole(
        personIdentifier: PersonIdentifier,
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<DigitalSale> = with(personIdentifier) {
        return when {
            role.isCO() -> getDigitalSaleConsultantList(code, campaigns)
            role.isSE() -> getDigitalSaleSeList(uaKey, campaigns)
            else -> emptyList()
        }
    }

    private fun getDigitalSaleConsultantList(
        consultantCode: String,
        campaigns: List<String>
    ): List<DigitalSaleConsultant> {
        return digitalSaleDataStore.getDigitalSaleConsultantList(consultantCode, campaigns)
            .map { digitalSaleMapper.map(it) }
    }

    private fun getDigitalSaleSeList(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<DigitalSaleBusinessPartner> {
        return digitalSaleDataStore.getDigitalSaleSeList(uaKey, campaigns)
            .map { digitalSaleMapper.map(it) }
    }
}
